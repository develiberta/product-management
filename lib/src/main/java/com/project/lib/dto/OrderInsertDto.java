package com.project.lib.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderInsertDto {
    private Integer count;

    private String productId;
}