package com.example.test.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException{
    private String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
