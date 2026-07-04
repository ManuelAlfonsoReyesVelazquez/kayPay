package com.karpay.client;

import com.karpay.client.dto.AchValidationRequest;
import com.karpay.client.dto.AchValidationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AchClient {

	@Autowired
	private WebClient achWebClient;

	@Retry(name = "ach")
	@CircuitBreaker(name = "ach")
	public AchValidationResponse validate(AchValidationRequest request) {

		return achWebClient.post().uri("/ach/validate").bodyValue(request).retrieve()
				.bodyToMono(AchValidationResponse.class).block(Duration.ofSeconds(60));

	}

}