package com.netcracker.fapi.services;

import com.netcracker.fapi.model.PostVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void createPost(MultipartFile[] images, Long ID, String description) throws IOException;

    List<PostVM> getUserPosts(String nickname, Integer page, Integer count);

    ResponseEntity<byte[]> getPostImage(Long userID, Integer postID, Integer image) throws IOException;

    List<PostVM> getUserFeed(Long ID, Integer page, Integer count);

    List<PostVM> getAllPostsInTwelveHours(Integer page, Integer count);

    List<PostVM> getPostsByHashtag(String hashtag, Integer page, Integer count);

    void deletePost(Long postID, Long authorID);
}
