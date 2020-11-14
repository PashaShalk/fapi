package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.Post;
import com.netcracker.fapi.model.PostVM;
import com.netcracker.fapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Value("${backend.url}/api/posts")
    private String backendURI;

    @Value("${fapi.url}/api/posts")
    private String fapiURI;

    @Value("${upload.path}")
    private String uploadPath;

    private final RestTemplate restTemplate;
    private final UserServiceImpl userService;

    @Autowired
    public PostServiceImpl(RestTemplateBuilder builder, UserServiceImpl userService) {
        this.restTemplate = builder.build();
        this.userService = userService;
    }

    @Override
    public void createPost(MultipartFile[] images, Long ID, String description) throws IOException {

        Post post = restTemplate.postForEntity(backendURI + "/user/" + ID + "/newpost", description, Post.class)
                .getBody();

        if (post != null) {
            StringBuilder photoPath = new StringBuilder(uploadPath)
                    .append("/").append(post.getAuthor().getID())
                    .append("/").append(post.getID());

            if (new File(photoPath.toString()).mkdir()) {
                for (int i = 0; i < images.length; i++) {
                    images[i].transferTo(new File(photoPath + "/" + i + ".png"));
                }
            }
        }
    }

    @Override
    public ResponseEntity<byte[]> getPostImage(Long userID, Integer postID, Integer imageNumber) throws IOException {
        File image = new File(uploadPath + "/" + userID + "/" + postID + "/" + imageNumber + ".png");

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(image.toPath()));
    }

    @Override
    public List<PostVM> getUserPosts(String nickname, Integer page, Integer count) {
        Post[] posts = restTemplate.getForEntity(backendURI + "/user/" + nickname
                        + "/page/" + page + "/count/" + count,
                Post[].class).getBody();
        List<PostVM> postsVM = new ArrayList<>();
        if (posts != null) {
            getPostsPhotosURIs(posts);
            for (Post post : posts) {
                postsVM.add(convertToPostVM(post));
            }
        }
        return postsVM;
    }

    @Override
    public List<PostVM> getUserFeed(Long ID, Integer page, Integer count) {
        Post[] posts = restTemplate.getForEntity(backendURI + "/feed/user/" + ID
                        + "/page/" + page + "/count/" + count,
                Post[].class).getBody();
        List<PostVM> postsVM = new ArrayList<>();
        if (posts != null) {
            getPostsPhotosURIs(posts);
            for (Post post : posts) {
                postsVM.add(convertToPostVM(post));
            }
        }
        return postsVM;
    }

    @Override
    public List<PostVM> getAllPostsInTwelveHours(Integer page, Integer count) {
        Post[] posts = restTemplate.getForEntity(backendURI + "/feed/page/" + page + "/count/" + count,
                Post[].class).getBody();
        List<PostVM> postsVM = new ArrayList<>();
        if (posts != null) {
            getPostsPhotosURIs(posts);
            for (Post post : posts) {
                postsVM.add(convertToPostVM(post));
            }
        }
        return postsVM;
    }

    @Override
    public List<PostVM> getPostsByHashtag(String hashtag, Integer page, Integer count) {
        Post[] posts = restTemplate.getForEntity(backendURI + "/feed/hashtag/" + hashtag
                        + "/page/" + page + "/count/" + count,
                Post[].class).getBody();
        List<PostVM> postsVM = new ArrayList<>();
        if (posts != null) {
            getPostsPhotosURIs(posts);
            for (Post post : posts) {
                postsVM.add(convertToPostVM(post));
            }
        }
        return postsVM;
    }

    @Override
    public void deletePost(Long postID, Long authorID) {
        restTemplate.getForEntity(backendURI + "/delete/" + postID, String.class);
        File postFolder = new File(uploadPath + "/" + authorID + "/" + postID);
        FileSystemUtils.deleteRecursively(postFolder);
    }

    private void getPostsPhotosURIs(Post[] posts) {
        for (Post post : posts) {
            getPostPhotosURIs(post);
        }
    }

    private void getPostPhotosURIs(Post post) {
        File[] images = new File(uploadPath
                + "/" + post.getAuthor().getID()
                + "/" + post.getID()).listFiles();
        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                post.getPhotoURIs().add(fapiURI
                        + "/user/" + post.getAuthor().getID()
                        + "/post/" + post.getID()
                        + "/image/" + i);
            }
        }
    }

    public PostVM convertToPostVM(Post post) {
        if (post != null) {
            getPostPhotosURIs(post);
            return PostVM.builder()
                    .ID(post.getID())
                    .author(userService.convertToUserVM(post.getAuthor()))
                    .date(post.getDate())
                    .description(post.getDescription())
                    .hashtags(post.getHashtags())
                    .photoURIs(post.getPhotoURIs())
                    .build();
        }
        return null;
    }
}
