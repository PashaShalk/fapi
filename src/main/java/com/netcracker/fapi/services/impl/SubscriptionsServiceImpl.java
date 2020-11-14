package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.SubscriptionsData;
import com.netcracker.fapi.services.SubscriptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SubscriptionsServiceImpl implements SubscriptionsService {

    @Value("${backend.url}/api/subscriptions")
    private String backendURI;

    private final RestTemplate restTemplate;

    @Autowired
    public SubscriptionsServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public ResponseEntity<SubscriptionsData> getSubscriptionsData(Long userID, Long authorizedUserID) {
        return restTemplate.getForEntity(backendURI
                        + "/user/"+ userID
                        + "/authorizeduser/" + authorizedUserID,
                SubscriptionsData.class);
    }

    @Override
    public ResponseEntity<SubscriptionsData> subscribe(Long userID, Long authorizedUserID) {
        return restTemplate.getForEntity(backendURI
                        + "/subscribing/user/"+ userID
                        + "/authorizeduser/" + authorizedUserID,
                SubscriptionsData.class);
    }

    @Override
    public ResponseEntity<SubscriptionsData> unsubscribe(Long userID, Long authorizedUserID) {
        return restTemplate.getForEntity(backendURI
                        + "/unsubscribing/user/"+ userID
                        + "/authorizeduser/" + authorizedUserID,
                SubscriptionsData.class);
    }
}
