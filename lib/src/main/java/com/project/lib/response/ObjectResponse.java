package com.project.lib.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ObjectResponse<T> extends ApiResponse<T> {
	protected T results;
	
	/* */
	public ObjectResponse(T results) {
		super();
		this.results = results;
	}
}
