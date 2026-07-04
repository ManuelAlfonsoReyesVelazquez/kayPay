package com.karpay.controller;

import com.karpay.dto.ValidationRequest;
import com.karpay.dto.ValidationResponse;
import com.karpay.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/validations")
public class ValidationController {

	@Autowired
    private ValidationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ValidationResponse createValidation(
            @Valid @RequestBody ValidationRequest request) {

        return service.createValidation(request);

    }

    @GetMapping("/{id}")
    public ValidationResponse getValidation(
            @PathVariable Long id) {

        return service.getValidation(id);

    }

}