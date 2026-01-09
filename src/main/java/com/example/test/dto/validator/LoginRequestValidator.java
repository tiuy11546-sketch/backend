package com.example.test.dto.validator;

import com.example.test.dto.request.LoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequest loginRequest = (LoginRequest) target;
        if(loginRequest.getUsername()==null || loginRequest.getUsername().isBlank()){
            errors.rejectValue("username","username.blank","Username should not be blank");
        }
        if(loginRequest.getPassword()==null || loginRequest.getPassword().isBlank()) {
            errors.rejectValue("password", "password.blank", "Password should not be blank");
        }
    }
}
