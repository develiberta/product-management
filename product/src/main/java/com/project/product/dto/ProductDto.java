package com.project.product.dto;

import com.project.product.type.Origin;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@ToString
public class ProductDto extends ProductSuperDto {
    private Date createdTime;

    private Date updatedTime;
}
