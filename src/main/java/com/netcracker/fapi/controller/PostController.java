package com.netcracker.fapi.controller;

import com.netcracker.fapi.model.PostVM;
import com.netcracker.fapi.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/{ID}/newpost")
    public void createPost(@RequestParam("images") MultipartFile[] images,
                           @RequestParam(value = "description", required = false) String description,
                           @PathVariable Long ID) throws IOException {
        postService.createPost(images, ID, description);
    }

    @GetMapping("/user/{userID}/post/{postID}/image/{imageNumber}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long userID,
                                            @PathVariable Integer postID,
                                            @PathVariable Integer imageNumber) throws IOException {
        return postService.getPostImage(userID, postID, imageNumber);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{nickname}/page/{page}/count/{count}")
    public List<PostVM> getUserPosts(@PathVariable String nickname,
                               @PathVariable Integer page,
                               @PathVariable Integer count) {
        return postService.getUserPosts(nickname, page, count);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/feed/user/{ID}/page/{page}/count/{count}")
    public List<PostVM> getUserFeed(@PathVariable Long ID,
                              @PathVariable Integer page,
                              @PathVariable Integer count) {
        return postService.getUserFeed(ID, page, count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/feed/page/{page}/count/{count}")
    public List<PostVM> getAllPostsIn12Hours(@PathVariable Integer page,
                                       @PathVariable Integer count) {
        return postService.getAllPostsInTwelveHours(page, count);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/hashtag/{hashtag}/page/{page}/count/{count}")
    public List<PostVM> getPostsByHashtag(@PathVariable String hashtag,
                                          @PathVariable Integer page,
                                          @PathVariable Integer count) {
        return postService.getPostsByHashtag(hashtag, page, count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/post/{postID}/author/{authorID}")
    public void deletePost(@PathVariable Long postID,
                           @PathVariable Long authorID) {
        postService.deletePost(postID, authorID);
    }
}
