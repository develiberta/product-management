package com.project.product.dto;

import com.project.product.type.Action;
import com.project.product.type.Origin;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@ToString
public class ProductHistoryDto extends ProductSuperDto {
    private Action action;

    private Date createdTime;

    private String productId;
}
