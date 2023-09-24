package com.loanStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

	private String status;
	private String statusCode;
	private String message;
	private Object response;
	private Object errorResponse;
}
