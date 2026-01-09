package com.example.test.controller;


import com.example.test.dto.request.LoginRequest;
import com.example.test.dto.response.AppResponse;
import com.example.test.dto.validator.LoginRequestValidator;
import com.example.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginRequestValidator loginRequestValidator;

    @PostMapping(path = "/login")
    public ResponseEntity<AppResponse> login(@RequestBody LoginRequest request){
        Errors errors = loginRequestValidator.validateObject(request);
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(AppResponse.error(errors.getAllErrors().getFirst().getDefaultMessage(),errors));
        }
        return ResponseEntity.ok(AppResponse.success("Login Successful", userService.login(request.getUsername(),request.getPassword())));
    }



}
