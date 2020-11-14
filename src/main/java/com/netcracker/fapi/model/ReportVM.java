package com.netcracker.fapi.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ReportVM {
    private long ID;
    private PostVM post;
    private String status;
    private Timestamp date;
    private String reason;
}

