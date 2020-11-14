package com.netcracker.fapi.services;

import com.netcracker.fapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    boolean registerUser(RegisteredUser registeredUser);

    UserVM getUserByID(Long ID);

    UserVM getUserByNickname(String nickname);

    List<UserVM> getAllUsers(Long page, Long count);

    Long getCountAllUsers();

    void blockUser(Long ID);

    void unblockUser(Long ID );

    void setAvatar(Long ID, MultipartFile avatar) throws IOException;

    ResponseEntity<byte[]> getAvatar(Long ID) throws IOException;

    UserVM updateInfo(UserInfo userInfo, Long ID);

    UserVM getUserByEmail(String email);
}
