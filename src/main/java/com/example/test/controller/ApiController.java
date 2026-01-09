package com.example.test.controller;

import com.example.test.dto.request.BankLoginRequest;
import com.example.test.dto.request.UserRequest;
import com.example.test.dto.response.AppResponse;
import com.example.test.service.BankLoginService;
import com.example.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1")
public class ApiController {
    private final UserService userService;
    private final BankLoginService bankLoginService;

    @PostMapping("/user")
    public ResponseEntity<AppResponse> getUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(AppResponse.success("user data",userService.getUserByUsername(userRequest.getUsername())));
    }

    @PostMapping("/bank-login")
    public ResponseEntity<AppResponse> createBankLogin(@RequestBody BankLoginRequest userRequest) {
        return ResponseEntity.ok(AppResponse.success("bank login created",bankLoginService.createLogin(userRequest)));
    }
}
