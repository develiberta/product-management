package com.project.lib.search;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class Search<T> {
	@ApiModelProperty(hidden = true)
    protected List<Specification<T>> specs = new ArrayList<>();

    /* */
    @ApiModelProperty(hidden = true)
    List<Specification<T>> getSpecs() {
        return this.specs;
    }

    public void setSpecs(List<Specification<T>> specs) {
        this.specs = specs;
    }

    public List<Specification<T>> buildSpecs() {
        SearchBinder.getObjectIdValue(this);
        return specs;
    }
}
