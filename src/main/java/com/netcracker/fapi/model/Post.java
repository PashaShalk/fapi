package com.netcracker.fapi.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
public class Post {
    private long ID;
    private User author;
    private String description;
    private Timestamp date;
    private Set<Hashtag> hashtags = new HashSet<>();
    private Set<String> photoURIs = new HashSet<>();
}
