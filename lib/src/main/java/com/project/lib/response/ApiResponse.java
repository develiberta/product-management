package com.project.lib.response;

import com.project.lib.constant.CommonConstant;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@ToString
public class ApiResponse<T> {
	protected Integer status;
	protected String error;
	protected String message;
	protected Date timestamp;
	
	/* */
	public ApiResponse() {
		this.status = HttpStatus.OK.value();
		this.error = HttpStatus.OK.name();
		this.message = CommonConstant.EMPTY_STR;
		this.timestamp = new Date();
	}	
	
	/* */
	public ApiResponse(HttpStatus status, Exception ex) {
		/* */
		this.status = status.value();
		this.error = status.name();
		if (ex != null) {
			this.message = ex.getMessage();
			if (StringUtils.isAllEmpty(this.message)) {
				this.message = ex.toString();
			}
		} else {
			this.message = this.error;
		}
		
		this.timestamp = new Date();
	}
}
