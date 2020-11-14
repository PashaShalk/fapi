package com.netcracker.fapi.controller;

import com.netcracker.fapi.model.SubscriptionsData;
import com.netcracker.fapi.services.impl.SubscriptionsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionsController {

    private final SubscriptionsServiceImpl subscriptionsService;

    @Autowired
    public SubscriptionsController(SubscriptionsServiceImpl subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userID}/authorizeduser/{authorizedUserID}")
    public ResponseEntity<SubscriptionsData> getSubscriptionsData(@PathVariable Long userID,
                                                                  @PathVariable Long authorizedUserID) {
        return subscriptionsService.getSubscriptionsData(userID, authorizedUserID);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/subscribing/user/{userID}/authorizeduser/{authorizedUserID}")
    public ResponseEntity<SubscriptionsData> subscribe(@PathVariable Long userID,
                                                       @PathVariable Long authorizedUserID) {
        return subscriptionsService.subscribe(userID, authorizedUserID);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/unsubscribing/user/{userID}/authorizeduser/{authorizedUserID}")
    public ResponseEntity<SubscriptionsData> unsubscribe(@PathVariable Long userID,
                                                         @PathVariable Long authorizedUserID) {
        return subscriptionsService.unsubscribe(userID, authorizedUserID);
    }
}
