package com.project.lib.dto;

import com.project.lib.type.Origin;
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
public class OrderDto {
    private String id;

    private Integer count;

    private String productId;

    private String productHistoryId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Origin origin;

    private Integer price;

    private Integer cost;

    private String image;

    private String description;

    private Date createdTime;

    private Date updatedTime;

    public void setProductInfo(String name, Origin origin, Integer price, Integer cost, String image, String description) {
        this.setName(name);
        this.setOrigin(origin);
        this.setPrice(price);
        this.setCost(cost);
        this.setImage(image);
        this.setDescription(description);
    }
}
