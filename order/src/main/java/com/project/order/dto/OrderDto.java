package com.project.order.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDto {
    private Integer count;

    private String productId;

    private String productHistoryId;
}
