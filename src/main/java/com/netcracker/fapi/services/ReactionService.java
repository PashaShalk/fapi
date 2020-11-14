package com.netcracker.fapi.services;

import com.netcracker.fapi.model.ReactionsData;
import org.springframework.http.ResponseEntity;

public interface ReactionService {

    ResponseEntity<ReactionsData> getReactionsData(Long userID, Long postID);

    ResponseEntity<ReactionsData> changeLikeStatus(Long userID, Long postID, Boolean likeStatus);

    ResponseEntity<ReactionsData> changeDislikeStatus(Long userID, Long postID, Boolean dislikeStatus);

    ResponseEntity<ReactionsData> changeLikeAndDislikeStatuses(Long userID, Long postID,
                                                               String likeStatus, Boolean dislikeStatus);
}
