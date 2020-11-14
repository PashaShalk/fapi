package com.netcracker.fapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVM {
    private long ID;
    private String email;
    private String nickname;
    private String firstName;
    private String lastName;
    private String profileDescription;
    private UserRoleEnum role;
    private UserStatusEnum status;
}
