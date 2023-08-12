package com.project.product.search;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecBuilder {

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Specification search(Search search) {

        List<Specification> specs = search.buildSpecs();

        Specification specifications = null;
        for (Specification spec : specs) {
            if (specifications == null) {
                specifications = Specification.where(spec);
            } else {
                specifications = specifications.and(spec);
            }
        }

        return specifications;
    }

}
