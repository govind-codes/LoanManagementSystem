package com.loanStore.scheduler;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.loanStore.entity.Loan;
import com.loanStore.repository.LoanRepository;
import com.loanStore.service.LoanService;


@Component
public class DueDateScheduler {
	
	@Autowired
	LoanRepository loanRepository;

	private static final Logger logger = Logger.getLogger(LoanService.class.getName());
@Scheduled(fixedDelay = 250000)
	public void checkOverdueLoans() {
		List<Loan> loans = loanRepository.findAll();

		LocalDate currentDate = LocalDate.now();

		for (Loan loan : loans) {
			if (currentDate.isAfter(loan.getDueDate())) {
				// The loan due date has been crossed
				String message = "Loan ID: " + loan.getLoanId() + " has crossed its due date. Due Date: "
						+ loan.getDueDate();
				logger.log(Level.WARNING, message);
			}
		}
	}
	}
