package com.example.test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankLoginResponse {
    private UUID id;
    private String bankUsername;
    private UUID userId;
    private String bankPassword;
    private String bankTransactionPassword;
    private String bankName;
    private String userName;

}
