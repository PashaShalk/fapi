package com.netcracker.fapi.services;

import com.netcracker.fapi.model.ReportVM;

import java.util.List;

public interface ReportService {

    void sendReport(Long ID, String reason);

    Long getCountAllReports();

    List<ReportVM> getAllReports(Long page, Long count);

    void markAsChecked(Long ID);

    void markAsUnchecked(Long ID);

    Long getCountUnreadReports();
}
