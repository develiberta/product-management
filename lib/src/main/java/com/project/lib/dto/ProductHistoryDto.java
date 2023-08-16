package com.project.lib.dto;

import com.project.lib.type.Action;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProductHistoryDto extends ProductSuperDto {
    private Action action;

    private Date createdTime;

    private String productId;
}
