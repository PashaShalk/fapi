package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.ReactionsData;
import com.netcracker.fapi.services.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Value("${backend.url}/api/reactions")
    private String backendURI;

    private final RestTemplate restTemplate;

    @Autowired
    public ReactionServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public ResponseEntity<ReactionsData> getReactionsData(Long userID, Long postID) {
        return restTemplate.getForEntity(backendURI
                        + "/user/"+ userID
                        + "/post/" + postID,
                ReactionsData.class);
    }

    @Override
    public ResponseEntity<ReactionsData> changeLikeStatus(Long userID, Long postID, Boolean likeStatus) {
        return restTemplate.getForEntity(backendURI
                        + "/user/"+ userID
                        + "/post/" + postID
                        + "/likeStatus/" + likeStatus,
                ReactionsData.class);
    }

    @Override
    public ResponseEntity<ReactionsData> changeDislikeStatus(Long userID, Long postID, Boolean dislikeStatus) {
        return restTemplate.getForEntity(backendURI
                        + "/user/"+ userID
                        + "/post/" + postID
                        + "/dislikeStatus/" + dislikeStatus,
                ReactionsData.class);
    }

    @Override
    public ResponseEntity<ReactionsData> changeLikeAndDislikeStatuses(Long userID, Long postID, String likeStatus, Boolean dislikeStatus) {
        return restTemplate.getForEntity(backendURI
                        + "/user/"+ userID
                        + "/post/" + postID
                        + "/likeStatus/" + likeStatus
                        + "/dislikeStatus/" + dislikeStatus,
                ReactionsData.class);
    }
}
