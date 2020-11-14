package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.Report;
import com.netcracker.fapi.model.ReportVM;
import com.netcracker.fapi.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Value("${backend.url}/api/reports")
    private String backendURI;

    private final RestTemplate restTemplate;
    private final PostServiceImpl postService;

    @Autowired
    public ReportServiceImpl(RestTemplateBuilder builder, PostServiceImpl postService) {
        this.restTemplate = builder.build();
        this.postService = postService;
    }

    @Override
    public void sendReport(Long ID, String reason) {
        restTemplate.postForEntity(backendURI + "/post/" + ID, reason, StringBuilder.class);
    }

    @Override
    public Long getCountAllReports() {
        return restTemplate.getForEntity(backendURI + "/count", Long.class).getBody();
    }

    @Override
    public Long getCountUnreadReports() {
        return restTemplate.getForEntity(backendURI + "/unread/count", Long.class).getBody();
    }

    @Override
    public List<ReportVM> getAllReports(Long page, Long count) {
        Report[] reports = restTemplate.getForEntity(backendURI + "/page/" + page + "/count/" + count,
                Report[].class)
                .getBody();
        List<ReportVM> reportsVM = new ArrayList<>();
        if (reports != null) {
            for (Report report : reports) {
                reportsVM.add(convertToReportVM(report));
            }
        }
        return reportsVM;
    }

    @Override
    public void markAsChecked(Long ID) {
        restTemplate.getForEntity(backendURI + "/checking/" + ID, String.class);
    }

    @Override
    public void markAsUnchecked(Long ID) {
        restTemplate.getForEntity(backendURI + "/unchecking/" + ID, String.class);
    }

    private ReportVM convertToReportVM(Report report) {
        if (report != null) {
            return ReportVM.builder()
                    .ID(report.getID())
                    .date(report.getDate())
                    .post(postService.convertToPostVM(report.getPost()))
                    .reason(report.getReason())
                    .status(report.getStatus().toString())
                    .build();
        }
        return null;
    }
}
