package com.loanStore.CustomException;

public class InvalidPaymentDateException extends RuntimeException {
	
	 public InvalidPaymentDateException(String message) {
	        super(message);
	    }
}
