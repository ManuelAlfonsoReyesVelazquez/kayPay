package com.karpay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karpay.entity.Validation;

@Repository
public interface ValidationRepository
        extends JpaRepository<Validation, Long> {

    Optional<Validation> findByRequestHash(
            String requestHash);

    Optional<Validation> findByAchReference(
            String reference);

}
