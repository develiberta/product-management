package com.project.lib.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InventoryDto {
    private String id;

    private Integer remaining;
}
