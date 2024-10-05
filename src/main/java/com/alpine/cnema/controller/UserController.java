package com.alpine.cnema.controller;

import com.alpine.cnema.dto.request.UserDTO;
import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.service.UserService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO.Register req){
        return RenderJson.basicFormat(
                userService.register(req),
                HttpStatus.OK,
                Messages.SUCCESS_CREATE_USER
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO.Login req){
        return RenderJson.basicFormat(
                userService.login(req),
                HttpStatus.OK,
                Messages.SUCCESS_LOGIN_USER
        );
    }
}

