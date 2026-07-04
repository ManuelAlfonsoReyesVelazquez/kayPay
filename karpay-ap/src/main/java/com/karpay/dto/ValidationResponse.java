package com.karpay.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponse {

    private Long validationId;

    private String status;

}