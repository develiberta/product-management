package com.project.product.dto.product;

import com.project.product.entity.ProductEntity;
import com.project.product.request.PageCondition;
import com.project.product.search.SearchCondition;
import com.project.product.search.SearchType;
import com.project.product.type.Origin;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ProductConditionalPageDto extends PageCondition<ProductEntity> {

    @ApiModelProperty(required = false)
    @SearchCondition(value = "id", condition = SearchType.EQUAL, qualifiedBy=ProductEntity.class)
    private Integer id;

    @ApiModelProperty(required = false)
    @SearchCondition(value = "name", condition = SearchType.LIKE, qualifiedBy=ProductEntity.class)
    private String name;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(required = false)
    @SearchCondition(value = "origin", condition = SearchType.EQUAL, qualifiedBy=ProductEntity.class)
    private Origin origin;

    private Integer price;

    private Integer cost;

    private String image;

    @ApiModelProperty(required = false)
    @SearchCondition(value = "description", condition = SearchType.LIKE, qualifiedBy=ProductEntity.class)
    private String description;

}
