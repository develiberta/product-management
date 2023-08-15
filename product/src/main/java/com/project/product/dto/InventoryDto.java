package com.project.product.dto;

import com.project.product.type.Origin;
import lombok.Data;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@ToString
public class InventoryDto {
    private String id;

    private Integer remaining;
}
