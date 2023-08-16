package com.project.lib.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ProductDto extends ProductSuperDto {
    private Integer remaining;

    private Date createdTime;

    private Date updatedTime;
}
