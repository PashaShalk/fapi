package com.netcracker.fapi.model;

import lombok.Data;

@Data
public class RegisteredUser {
    private String email;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String profileDescription;
}
