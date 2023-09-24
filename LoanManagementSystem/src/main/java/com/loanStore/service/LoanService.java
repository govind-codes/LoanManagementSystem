package com.loanStore.service;


import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.query.Query;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanStore.CustomException.InvalidPaymentDateException;
import com.loanStore.CustomException.LoanNotFoundException;
import com.loanStore.dto.LoanAggregationResult;
import com.loanStore.dto.ResponseDto;
import com.loanStore.entity.Loan;
import com.loanStore.repository.LoanRepository;

import jakarta.persistence.EntityManager;

@Service
public class LoanService {

	private static final Logger logger = Logger.getLogger(LoanService.class.getName());

	@Autowired
	LoanRepository loanRepository;
	@Autowired
	private EntityManager entityManager;

	public ResponseDto addLoan(Loan loan) {

	            if (loan.getPaymentDate().compareTo(loan.getDueDate()) > 0) {
	                throw new InvalidPaymentDateException("Loan has been rejected as Payment date: " + loan.getPaymentDate()
	                        + " cannot be greater than Due date: " + loan.getDueDate());
	            }

	            loanRepository.save(loan);

	            return new ResponseDto("success", "200", "Loan added successfully", loan, false);
	      
	}

	public List<Loan> getAllLoans() {
		// TODO Auto-generated method stub
		List<Loan> loans = loanRepository.findAll();

		return loans;
	}

	public Loan getLoanById(String loanId) {
		// TODO Auto-generated method stub
		Loan loan = loanRepository.findById(loanId).orElse(null);

		if (loan == null) {
			throw new LoanNotFoundException("Loan with ID " + loanId + " not found");
		}
		return loan;
	}

	public List<Loan> getLoansByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		List<Loan> loanByListCustomer = loanRepository.findByCustomerId(customerId);
		return loanByListCustomer;
	}

	public List<Loan> getLoansByLenderId(String lenderId) {
		// TODO Auto-generated method stub
		List<Loan> LoansByLenderId = loanRepository.findByLenderId(lenderId);
		return LoansByLenderId;
	}

	public List<LoanAggregationResult> aggregateLoansByLender(String lenderId) {
      logger.info("arregating data asper lender"+ lenderId);
		String jpql = "SELECT l.lenderId, SUM(l.remainingAmount), SUM(l.interestPerDay), SUM(l.penaltyPerDay) "
				+ "FROM Loan l WHERE l.lenderId = :lenderId GROUP BY l.lenderId";
		
		Query query = (Query) entityManager.createQuery(jpql);
		query.setParameter("lenderId", lenderId);
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();
		 
		// Convert the results to LoanAggregationResult DTOs
		List<LoanAggregationResult> aggregates = results.stream().map(row -> {
			LoanAggregationResult aggregate = new LoanAggregationResult();
			aggregate.setTotalRemainingAmount((Double) row[1]);
			aggregate.setTotalInterest((Double) row[2]);
			aggregate.setTotalPenalty((Double) row[3]);
			return aggregate;
		}).collect(Collectors.toList());
		logger.info("Step 2 arregating data "+ aggregates);
		return aggregates;

	}

	public List<LoanAggregationResult> aggregateLoansByCustomerId(String customerId) {
		String jpql = "SELECT l.customerId, SUM(l.remainingAmount), SUM(l.interestPerDay), SUM(l.penaltyPerDay) "
				+ "FROM Loan l WHERE l.customerId = :customerId GROUP BY l.customerId";

		Query query = (Query) entityManager.createQuery(jpql);
		query.setParameter("customerID", customerId);
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		// Convert the results to LoanAggregationResult DTOs
		List<LoanAggregationResult> aggregates = results.stream().map(row -> {
			LoanAggregationResult aggregate = new LoanAggregationResult();

			aggregate.setTotalRemainingAmount((Double) row[1]);
			aggregate.setTotalInterest((Double) row[2]);
			aggregate.setTotalPenalty((Double) row[3]);
			return aggregate;
		}).collect(Collectors.toList());

		return aggregates;
	}

	public List<LoanAggregationResult> aggregateLoansByInterest(double interestPerDay) {
		String jpql = "SELECT l.interestPerDay, SUM(l.remainingAmount), SUM(l.interestPerDay), SUM(l.penaltyPerDay) "
				+ "FROM Loan l WHERE l.interestPerDay = :interestPerDay GROUP BY l.interestPerDay";
			
				Query query = (Query) entityManager.createQuery(jpql);
				query.setParameter("interestPerDay", interestPerDay);
		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		// Convert the results to LoanAggregationResult DTOs
		List<LoanAggregationResult> aggregates = results.stream().map(row -> {
			LoanAggregationResult aggregate = new LoanAggregationResult();
			aggregate.setTotalRemainingAmount((Double) row[1]);
			aggregate.setTotalInterest((Double) row[2]);
			aggregate.setTotalPenalty((Double) row[3]);
			return aggregate;
		}).collect(Collectors.toList());

		return aggregates;

	}

	// Logging
	
//	public void checkOverdueLoans() {
//		List<Loan> loans = loanRepository.findAll();
//
//		LocalDate currentDate = LocalDate.now();
//
//		for (Loan loan : loans) {
//			if (currentDate.isAfter(loan.getDueDate())) {
//				// The loan due date has been crossed
//				String message = "Loan ID: " + loan.getLoanId() + " has crossed its due date. Due Date: "
//						+ loan.getDueDate();
//				logger.log(Level.WARNING, message);
//			}
//		}

	//}

}
