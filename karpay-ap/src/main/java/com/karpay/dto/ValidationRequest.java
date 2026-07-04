package com.karpay.dto;

import com.karpay.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ValidationRequest {

    @NotBlank
    private String customerDocument;

    @NotBlank
    private String bankCode;

    @NotNull
    private AccountType accountType;

    @NotBlank
    private String accountNumber;

}