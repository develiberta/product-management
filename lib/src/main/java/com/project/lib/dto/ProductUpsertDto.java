package com.project.lib.dto;

import com.project.lib.type.Origin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@ToString
public class ProductUpsertDto {
    private String name;

    @Enumerated(EnumType.STRING)
    private Origin origin;

    private Integer price;

    private Integer cost;

    private String image;

    private String description;
}
