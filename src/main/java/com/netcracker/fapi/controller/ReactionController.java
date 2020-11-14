package com.netcracker.fapi.controller;

import com.netcracker.fapi.model.ReactionsData;
import com.netcracker.fapi.services.impl.ReactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reactions")
public class ReactionController {

    private final ReactionServiceImpl reactionService;

    @Autowired
    public ReactionController(ReactionServiceImpl reactionService) {
        this.reactionService = reactionService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userID}/post/{postID}")
    public ResponseEntity<ReactionsData> getReactionData(@PathVariable Long userID,
                                                         @PathVariable Long postID) {
        return reactionService.getReactionsData(userID, postID);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userID}/post/{postID}/likeStatus/{likeStatus}")
    public ResponseEntity<ReactionsData> changeLikeStatus(@PathVariable Long userID,
                                          @PathVariable Long postID,
                                          @PathVariable Boolean likeStatus) {
        return reactionService.changeLikeStatus(userID, postID, likeStatus);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userID}/post/{postID}/dislikeStatus/{dislikeStatus}")
    public ResponseEntity<ReactionsData> changeDislikeStatus(@PathVariable Long userID,
                                             @PathVariable Long postID,
                                             @PathVariable Boolean dislikeStatus) {
        return reactionService.changeDislikeStatus(userID, postID, dislikeStatus);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userID}/post/{postID}/likeStatus/{likeStatus}/dislikeStatus/{dislikeStatus}")
    public ResponseEntity<ReactionsData> changeLikeAndDislikeStatuses(@PathVariable Long userID,
                                                      @PathVariable Long postID,
                                                      @PathVariable Boolean dislikeStatus,
                                                      @PathVariable String likeStatus) {
        return reactionService.changeLikeAndDislikeStatuses(userID, postID, likeStatus, dislikeStatus);
    }
}
