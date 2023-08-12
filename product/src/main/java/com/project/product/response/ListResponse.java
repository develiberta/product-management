package com.project.product.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ListResponse<T> extends ApiResponse<T> {
	protected List<T> results;
	
	/* */
	public ListResponse(List<T> results) {
		super();
		this.results = results;
	}
}
