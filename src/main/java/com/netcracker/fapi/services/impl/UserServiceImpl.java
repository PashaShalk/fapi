package com.netcracker.fapi.services.impl;

import com.netcracker.fapi.model.*;
import com.netcracker.fapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("UserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${backend.url}/api/users")
    private String backendURI;

    @Value("${upload.path}")
    private String uploadPath;

    private final RestTemplate restTemplate;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(RestTemplateBuilder builder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.restTemplate = builder.build();
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean registerUser(RegisteredUser registeredUser) {
        if (registeredUser.getPassword().equals(registeredUser.getConfirmPassword())) {
            User user = User.builder()
                    .email(registeredUser.getEmail())
                    .nickname(registeredUser.getNickname())
                    .firstName(registeredUser.getFirstName())
                    .lastName(registeredUser.getLastName())
                    .profileDescription(registeredUser.getProfileDescription())
                    .password(bCryptPasswordEncoder.encode(registeredUser.getPassword()))
                    .build();

            User newUser = restTemplate.postForEntity(backendURI + "/registration",
                    user, User.class).getBody();

            if (newUser != null) {
                return new File(uploadPath + "/" + newUser.getID()).mkdir();
            }
        }
        return false;
    }

    @Override
    public UserVM getUserByID(Long ID) {
        User user = restTemplate.getForEntity(backendURI + "/id/" + ID, User.class).getBody();
        return convertToUserVM(user);
    }

    @Override
    public UserVM getUserByNickname(String nickname) {
        User user = restTemplate.getForEntity(backendURI + "/nickname/" + nickname, User.class).getBody();
        return convertToUserVM(user);
    }

    @Override
    public UserVM getUserByEmail(String email) {
        User user = restTemplate.getForEntity(backendURI + "/email/" + email, User.class).getBody();
        return convertToUserVM(user);
    }

    @Override
    public List<UserVM> getAllUsers(Long page, Long count) {
        User[] users = restTemplate.getForEntity(backendURI + "/page/" + page + "/count/" + count, User[].class)
                .getBody();
        List<UserVM> usersVM = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                usersVM.add(convertToUserVM(user));
            }
        }
        return usersVM;
    }

    @Override
    public Long getCountAllUsers() {
        return restTemplate.getForEntity(backendURI + "/count", Long.class).getBody();
    }

    @Override
    public void blockUser(Long ID) {
        restTemplate.getForEntity(backendURI + "/blocking/" + ID, String.class);
    }

    @Override
    public void unblockUser(Long ID) {
        restTemplate.getForEntity(backendURI + "/unblocking/" + ID, String.class);
    }

    @Override
    public void setAvatar(Long ID, MultipartFile avatar) throws IOException {
        if (avatar != null) {
            avatar.transferTo(new File(uploadPath + "/" + ID + "/avatar.png"));
        }
    }

    @Override
    public ResponseEntity<byte[]> getAvatar(Long ID) throws IOException {
        File avatar = new File(uploadPath + "/" + ID + "/avatar.png");

        if (!avatar.exists() || avatar.isDirectory()) {
            avatar = new File(uploadPath + "/defaultAvatar.png");
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(avatar.toPath()));
    }

    @Override
    public UserVM updateInfo(UserInfo userInfo, Long ID) {
        User user = User.builder()
                .ID(ID)
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .profileDescription(userInfo.getProfileDescription())
                .build();

        return convertToUserVM(restTemplate.postForEntity(backendURI + "/updating", user, User.class)
                .getBody());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserVM convertToUserVM(User user) {
        if (user != null) {
            return UserVM.builder()
                    .ID(user.getID())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .profileDescription(user.getProfileDescription())
                    .role(user.getRole().getRole())
                    .status(user.getStatus().getStatus())
                    .build();
        }
        return null;
    }

    private User findByEmail(String email) {
        return restTemplate.getForEntity(backendURI + "/email/" + email, User.class).getBody();
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
        return authorities;
    }
}
