package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.Comment;
import com.netcracker.fapi.services.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommentServiceImpl implements CommentService {

    @Value("${backend.url}/api/comments")
    private String backendURI;

    private final RestTemplate restTemplate;

    public CommentServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public Comment[] getCommentByPostID(Long postID) {
        return restTemplate.getForEntity(backendURI + "/post/" + postID, Comment[].class).getBody();
    }

    @Override
    public void sendComment(Long postID, Long userID, String text) {
        restTemplate.postForEntity(backendURI + "/post/" + postID + "/user/" + userID, text, String.class);
    }
}
