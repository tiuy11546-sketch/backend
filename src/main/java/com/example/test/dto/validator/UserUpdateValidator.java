package com.example.test.dto.validator;

import com.example.test.dto.request.UserUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserUpdateRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateRequest request = (UserUpdateRequest) target;

    }
}
