package com.netcracker.fapi.services;

import com.netcracker.fapi.model.SubscriptionsData;
import org.springframework.http.ResponseEntity;

public interface SubscriptionsService {
    ResponseEntity<SubscriptionsData> getSubscriptionsData(Long userID, Long authorizedUserID);

    ResponseEntity<SubscriptionsData> subscribe(Long userID, Long authorizedUserID);

    ResponseEntity<SubscriptionsData> unsubscribe(Long userID, Long authorizedUserID);
}
