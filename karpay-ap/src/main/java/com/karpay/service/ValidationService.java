package com.karpay.service;

import com.karpay.audit.AuditService;
import com.karpay.client.AchClient;
import com.karpay.client.dto.AchValidationRequest;
import com.karpay.client.dto.AchValidationResponse;
import com.karpay.dto.ValidationRequest;
import com.karpay.dto.ValidationResponse;
import com.karpay.entity.*;
import com.karpay.enums.ValidationStatus;
import com.karpay.exception.ValidationNotFoundException;
import com.karpay.repository.BankAccountRepository;
import com.karpay.repository.CustomerRepository;
import com.karpay.repository.ValidationRepository;
import com.karpay.util.HashUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {

	@Autowired
    private  ValidationRepository validationRepository;
	@Autowired
    private  CustomerRepository customerRepository;
	@Autowired
    private  BankAccountRepository bankAccountRepository;
	@Autowired
    private  AuditService auditService;
	@Autowired
	private AchClient achClient;
//	@Autowired
//	private ValidationPersistenceService validationPersistenceService;
	
//    @Transactional
    public ValidationResponse createValidation(
            ValidationRequest request) {

        String requestHash =
                buildHash(request);

        var existingValidation =
                validationRepository.findByRequestHash(
                        requestHash);

        if (existingValidation.isPresent()) {

            Validation validation =
                    existingValidation.get();

            return ValidationResponse.builder()
                    .validationId(validation.getId())
                    .status(validation.getStatus().name())
                    .build();

        }

        Customer customer =
                customerRepository
                        .findByDocumentNumber(
                                request.getCustomerDocument())
                        .orElseGet(() -> {

                            Customer newCustomer =
                                    Customer.builder()
                                            .documentType("CC")
                                            .documentNumber(
                                                    request.getCustomerDocument())
                                            .createdAt(LocalDateTime.now())
                                            .build();

                            return customerRepository.save(
                                    newCustomer);

                        });

        BankAccount account =
                bankAccountRepository
                        .findByAccountNumber(
                                request.getAccountNumber())
                        .orElseGet(() -> {

                            BankAccount newAccount =
                                    BankAccount.builder()
                                            .customer(customer)
                                            .bankCode(request.getBankCode())
                                            .accountType(
                                                    request.getAccountType())
                                            .accountNumber(
                                                    request.getAccountNumber())
                                            .status("ACTIVE")
                                            .createdAt(LocalDateTime.now())
                                            .build();

                            return bankAccountRepository.save(
                                    newAccount);

                        });

        Validation validation =
                Validation.builder()
                        .account(account)
                        .requestId(UUID.randomUUID())
                        .requestHash(requestHash)
                        .status(ValidationStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
        
        validation = validationRepository.save(validation);
        
        AchValidationRequest achRequest =
                AchValidationRequest.builder()
                        .customerDocument(
                                request.getCustomerDocument())
                        .bankCode(
                                request.getBankCode())
                        .accountType(
                                request.getAccountType().name())
                        .accountNumber(
                                request.getAccountNumber())
                        .requestHash(requestHash)
                        .build();
        
        

        AchValidationResponse achResponse =
                achClient.validate(achRequest);
        
        validation.setAchReference(
                achResponse.getReference());

        validation.setStatus(
                ValidationStatus.PROCESSING);

        validation.setUpdatedAt(
                LocalDateTime.now());

        validation = validationRepository.save(validation);
        

        auditService.saveEvent(
                validation.getId(),
                "VALIDATION_CREATED",
                request);

        return ValidationResponse.builder()
                .validationId(validation.getId())
                .status(validation.getStatus().name())
                .build();

    }

    public ValidationResponse getValidation(
            Long id) {

        Validation validation =
                validationRepository.findById(id)
                        .orElseThrow(() ->
                                new ValidationNotFoundException(
                                        "Validation not found"));

        return ValidationResponse.builder()
                .validationId(validation.getId())
                .status(validation.getStatus().name())
                .build();

    }

    private String buildHash(ValidationRequest request) {

        String value = new StringBuilder()
                .append(request.getCustomerDocument())
                .append("|")
                .append(request.getBankCode())
                .append("|")
                .append(request.getAccountType())
                .append("|")
                .append(request.getAccountNumber())
                .toString();

        return HashUtils.sha256(value);
    }
    

}