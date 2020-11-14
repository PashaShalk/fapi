package com.netcracker.fapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizedUser {
    private long ID;
    private UserRoleEnum role;
    private UserStatusEnum status;
}
