package com.netcracker.fapi.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Report {
    private long ID;
    private Post post;
    private ReportStatusEnum status;
    private Timestamp date;
    private String reason;
}
