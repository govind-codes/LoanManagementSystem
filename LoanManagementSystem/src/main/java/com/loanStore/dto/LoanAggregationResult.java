package com.loanStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAggregationResult {
	private double totalRemainingAmount;
	private double totalInterest;
	private double totalPenalty;

}
