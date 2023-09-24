package com.loanStore.entity;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {

    @Id
	@GeneratedValue(generator = "CustomLoanIdGenerator")
	@GenericGenerator(name = "CustomLoanIdGenerator", strategy  = "com.loanStore.CustomLoanIdGenerator.CustomLoanIdGenerator")
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private LocalDate paymentDate;
    private double interestPerDay;
    private LocalDate dueDate;
    private double penaltyPerDay;
    private boolean cancel;

   
}

