package com.karpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karpay.entity.ValidationEvent;

@Repository
public interface ValidationEventRepository
        extends JpaRepository<ValidationEvent, Long> {
}
