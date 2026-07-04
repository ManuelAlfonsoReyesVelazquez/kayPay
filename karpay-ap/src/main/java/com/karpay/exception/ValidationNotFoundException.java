package com.karpay.exception;

public class ValidationNotFoundException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ValidationNotFoundException(String message) {
        super(message);
    }

}