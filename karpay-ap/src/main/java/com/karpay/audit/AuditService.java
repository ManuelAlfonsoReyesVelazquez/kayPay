package com.karpay.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karpay.entity.ValidationEvent;
import com.karpay.repository.ValidationEventRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

	@Autowired
    private ValidationEventRepository repository;
	@Autowired
    private ObjectMapper objectMapper;

    public void saveEvent(
            Long validationId,
            String eventType,
            Object payload) {

        try {

            ValidationEvent event =
                    ValidationEvent.builder()
                            .validationId(validationId)
                            .eventType(eventType)
                            .payload(
                                    objectMapper.writeValueAsString(payload)
                            )
                            .createdAt(LocalDateTime.now())
                            .build();

            repository.save(event);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);

        }

    }

}