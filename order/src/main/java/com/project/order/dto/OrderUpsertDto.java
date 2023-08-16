package com.project.order.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderUpsertDto {
    private Integer count;

    private String productId;
}
