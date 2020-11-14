package com.netcracker.fapi.services;

import com.netcracker.fapi.model.Comment;

public interface CommentService {
    Comment[] getCommentByPostID(Long postID);

    void sendComment(Long postID, Long userID, String text);
}
