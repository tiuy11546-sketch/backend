package com.example.test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankLoginRequest {
    private String bankUsername;
    private String bankPassword;
    private String bankName;
    private String userName;
    private UUID userId;
    private String corporateId;

}
