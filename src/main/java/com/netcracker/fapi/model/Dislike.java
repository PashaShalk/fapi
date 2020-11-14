package com.netcracker.fapi.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Dislike {
    private long ID;
    private User author;
    private Post post;
    private Timestamp date;
}
