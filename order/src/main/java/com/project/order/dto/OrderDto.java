package com.project.order.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDto {
    private String id;

    private Integer count;

    private Integer price;

    private String productId;
}