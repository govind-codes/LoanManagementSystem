package com.loanStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loanStore.dto.LoanAggregationResult;
import com.loanStore.dto.ResponseDto;
import com.loanStore.entity.Loan;
import com.loanStore.service.LoanService;

@RestController
public class LoanController {
	@Autowired
	private LoanService loanService;

	@GetMapping
	public List<Loan> getAllLoans()  {
		
	List<Loan>resultList=loanService.getAllLoans();
	//loanService.checkOverdueLoans();
		return resultList;
	}
//	throw new InvalidPaymentDateException("Loan has been rejected as Payment date: " + loan.getPaymentDate()
//	+ " cannot be grater then Due date: " + loan.getDueDate());

	@PostMapping("/add")
	public ResponseDto addLoan(@RequestBody Loan loan) {
		return loanService.addLoan(loan);
	}

	@GetMapping("/{loanId}")
	public Loan getLoanById(@PathVariable String loanId) {

		return loanService.getLoanById(loanId);
	}

	@GetMapping("/customer/{customerId}")
	public List<Loan> getLoansByCustomerId(@PathVariable String customerId) {
		return loanService.getLoansByCustomerId(customerId);
	}

	@GetMapping("/lender/{lenderId}")
	public List<Loan> getLoansByLenderId(@PathVariable String lenderId) {
		return loanService.getLoansByLenderId(lenderId);
	}

	@GetMapping("/aggregate/lender/{lenderId}")
	public List<LoanAggregationResult> aggregateLoansByLender(@PathVariable String lenderId) {
	    return loanService.aggregateLoansByLender(lenderId);
	}

	@GetMapping("/aggregate/customer/{customerId}")
	public List<LoanAggregationResult> aggregateLoansByCustomerId(@PathVariable String customerId) {
		return loanService.aggregateLoansByCustomerId(customerId);
	}

	@GetMapping("/aggregate/interest/{interest}")
	public List<LoanAggregationResult> aggregateLoansByInterest(@PathVariable double interest) {
		return loanService.aggregateLoansByInterest(interest);
	}

}
