package com.karpay.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.karpay.enums.ValidationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "validations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Validation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private BankAccount account;

	private UUID requestId;

	private String requestHash;

	@Enumerated(EnumType.STRING)
	private ValidationStatus status;

	private String achReference;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
