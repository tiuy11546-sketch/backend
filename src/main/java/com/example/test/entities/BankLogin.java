package com.example.test.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "bank_logins")
@Data
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class BankLogin {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "bank_username", nullable = false)
    private String bankUsername;

    @Column(name = "bank_password", nullable = false)
    private String bankPassword;

    @Column(name = "bank_transaction_password")
    private String bankTransactionPassword;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "corporate_id")
    private String corporateId;


}
