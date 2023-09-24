package com.loanStore.GlobalExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loanStore.CustomException.InvalidPaymentDateException;
import com.loanStore.CustomException.LoanNotFoundException;
import com.loanStore.dto.ResponseDto;
import com.loanStore.entity.Loan;
@RestControllerAdvice
public class LoanExceptionHandling {
Loan loan=new Loan();
	  @ExceptionHandler(LoanNotFoundException.class)
	    public ResponseEntity<ResponseDto> handleLoanNotFoundException(LoanNotFoundException ex) {
	        ResponseDto responseDto = new ResponseDto("error", HttpStatus.NOT_FOUND.toString(), ex.getMessage(),loan, true);
	        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(InvalidPaymentDateException.class)
	    public ResponseEntity<ResponseDto> handleInvalidPaymentDateException(InvalidPaymentDateException ex) {
	        ResponseDto responseDto = new ResponseDto("error", HttpStatus.BAD_REQUEST.toString(), ex.getMessage(), loan, true);
	        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
	    }
}
