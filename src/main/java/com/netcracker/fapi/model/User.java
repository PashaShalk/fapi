package com.netcracker.fapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long ID;
    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private String password;
    private UserRole role;
    private UserStatus status;
}
