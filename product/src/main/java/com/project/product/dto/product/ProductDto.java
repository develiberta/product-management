package com.project.product.dto.product;

import com.project.product.type.Origin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@ToString
public class ProductDto {
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Origin origin;

    private Integer price;

    private Integer cost;

    private String image;

    private String description;
}
