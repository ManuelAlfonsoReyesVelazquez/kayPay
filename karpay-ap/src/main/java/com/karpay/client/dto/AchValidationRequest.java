package com.karpay.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchValidationRequest {

    private String customerDocument;

    private String bankCode;

    private String accountType;

    private String accountNumber;
    
    private String requestHash;

}
