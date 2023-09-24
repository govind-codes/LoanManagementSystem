package com.loanStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loanStore.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, String> {

	List<Loan> findByCustomerId(String customerId);

	List<Loan> findByLenderId(String lenderId);
	
	

}
 