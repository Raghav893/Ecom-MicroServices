package com.raghav.userservice.controller;

import com.raghav.userservice.Entity.User;
import com.raghav.userservice.common.response.ApiResponse;
import com.raghav.userservice.dto.LoginDTO;
import com.raghav.userservice.dto.RegisterDTO;
import com.raghav.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterDTO dto) {

        User user = service.register(dto);
        ApiResponse<User> response = new ApiResponse<>(
                true,
                "user registered succesfully",
                user,
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO dto) {
        String jwt = service.verify(dto);
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "jwt token: ",
                jwt,
                null

        );
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
}
