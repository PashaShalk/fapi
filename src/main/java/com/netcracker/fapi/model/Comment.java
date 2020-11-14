package com.netcracker.fapi.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Comment {
    private long ID;
    private User author;
    private Post post;
    private String text;
    private Timestamp date;
}
