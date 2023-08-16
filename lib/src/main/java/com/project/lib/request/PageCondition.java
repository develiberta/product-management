package com.project.lib.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageCondition<T> extends SpecCondition<T> {
	/* */
	public static int START_PAGE_NO = 0;
	
	/* */
	@ApiModelProperty(value="rows of page", required=true, example="10")
	private int size;

	@ApiModelProperty(value="page to view", required=true, example="0")
	private int page;
	
	/* */
	@ApiModelProperty(required=false)
	private String sort;
	
	/* */
	public PageRequest makePageable() {
		if (this.sort == null) {
			return PageRequest.of(this.page, this.size);
		}
		
		String sortField = "";
		String direction = "";
		
		/* */
		String[] tokens = this.sort.split(",");
		if (tokens == null || tokens.length == 0) {
			return PageRequest.of(this.page, this.size);
		}
		
		/* */
		if (tokens.length >= 1) {
			sortField = tokens[0].trim();
		}
		if (tokens.length >= 2) {
			direction = tokens[1].trim();
		} 
			
		return PageRequest.of(page, size, Direction.fromString(direction), sortField);
	}
}
 