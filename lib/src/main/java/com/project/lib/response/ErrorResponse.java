package com.project.lib.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends ApiResponse<String> {
	/* */
	public ErrorResponse(HttpStatus error, Exception ex) {
		super(error, ex);
	}
}
