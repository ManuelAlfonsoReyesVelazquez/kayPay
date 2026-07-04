//package com.karpay.service;
//
//import com.karpay.entity.*;
//import com.karpay.repository.ValidationRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//public class ValidationPersistenceService {
//
//	@Autowired
//	private ValidationRepository validationRepository;
//
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	public Validation saveValidation(Validation validation) {
//		return validationRepository.save(validation);
//	}
//}
