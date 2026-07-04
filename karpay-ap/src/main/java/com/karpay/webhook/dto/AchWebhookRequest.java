package com.karpay.webhook.dto;

import lombok.Data;

@Data
public class AchWebhookRequest {

    private String reference;

    private String status;
    private String requestHash;

}