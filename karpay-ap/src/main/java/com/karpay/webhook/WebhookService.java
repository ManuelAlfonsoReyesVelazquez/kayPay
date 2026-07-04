package com.karpay.webhook;

import com.karpay.audit.AuditService;
import com.karpay.entity.Validation;
import com.karpay.enums.ValidationStatus;
import com.karpay.repository.ValidationRepository;
import com.karpay.webhook.dto.AchWebhookRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebhookService {

	@Autowired
    private ValidationRepository validationRepository;

	@Autowired
    private AuditService auditService;

    @Transactional
    public void process(
            AchWebhookRequest request) {

    	Validation validation =
    	        validationRepository
    	                .findByAchReference(request.getReference())
    	                .or(() -> validationRepository.findByRequestHash(request.getRequestHash()))
    	                .orElseThrow();

        auditService.saveEvent(
                validation.getId(),
                "WEBHOOK_RECEIVED",
                request);

        ValidationStatus newStatus =
                ValidationStatus.valueOf(
                        request.getStatus());

        
        if (validation.getStatus()
                .equals(newStatus)) {

            return;
        }

        validation.setStatus(newStatus);

        validation.setUpdatedAt(
                LocalDateTime.now());
        validation.setAchReference(request.getReference());

        validationRepository.save(validation);

        auditService.saveEvent(
                validation.getId(),
                "STATUS_UPDATED",
                request);

    }

}