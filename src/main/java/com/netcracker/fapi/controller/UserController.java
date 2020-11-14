package com.netcracker.fapi.controller;

import com.netcracker.fapi.model.RegisteredUser;
import com.netcracker.fapi.model.UserInfo;
import com.netcracker.fapi.model.UserVM;
import com.netcracker.fapi.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    public final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/registration")
    public boolean register(@RequestBody RegisteredUser registeredUser) {
        return userService.registerUser(registeredUser);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/id/{ID}")
    public UserVM getUserByID(@PathVariable Long ID) {
        return userService.getUserByID(ID);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/nickname/{nickname}")
    public UserVM getUserByNickname(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email/{email}")
    public UserVM getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page/{page}/count/{count}")
    public List<UserVM> getAllUsers(@PathVariable Long page, @PathVariable Long count) {
        return userService.getAllUsers(page, count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/count")
    public Long getCountAllUsers() {
        return userService.getCountAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/blocking/{ID}")
    public void blockUser(@PathVariable Long ID) {
        userService.blockUser(ID);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/unblocking/{ID}")
    public void unblockUser(@PathVariable Long ID) {
        userService.unblockUser(ID);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/newavatar/{ID}")
    public HttpStatus uploadAvatar(@RequestParam("file") MultipartFile avatar,
                                   @PathVariable Long ID) throws IOException {
        userService.setAvatar(ID, avatar);
        return HttpStatus.OK;
    }

    @GetMapping("/avatar/{ID}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long ID) throws IOException {
        return userService.getAvatar(ID);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/updating/{ID}")
    public UserVM updateInfo(@RequestBody UserInfo userInfo,
                             @PathVariable Long ID) {
        return userService.updateInfo(userInfo, ID);
    }
}
