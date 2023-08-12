package com.project.order.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class PageResponse<T> extends ApiResponse<T> {
	protected long 		totalPages;
	protected long 		totalElements;
	protected boolean 	last;
	protected int 		number;
	protected boolean 	first;
	protected int 		size;
	protected int	 	numberOfElements;
	
	/* */
	protected int		page;
	
	/* */
	protected List<T> results;
	
	/* */
	public PageResponse(Page<T> results) {
		super();
		
		this.results = results.getContent();
		
		/* */
		this.totalPages 	= results.getTotalPages();
		this.totalElements 	= results.getTotalElements();
		this.size 			= results.getSize();
		this.number 		= results.getNumber();
		
		/* */
		this.numberOfElements = results.getPageable().getPageSize();
		
		this.page = results.getPageable().getPageNumber();
		
		this.first =  (page == 0 ? true : false);
		this.last = ((page+1) == this.totalPages ? true : false);
	}
}
