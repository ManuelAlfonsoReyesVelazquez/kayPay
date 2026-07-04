package com.karpay.webhook;

import com.karpay.webhook.dto.AchWebhookRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

	@Autowired
    private WebhookService service;

    @PostMapping("/ach")
//    @Async
    public ResponseEntity<Void> processAchWebhook(
            @RequestBody
            AchWebhookRequest request) {

        service.process(request);

        return ResponseEntity.ok().build();

    }

}