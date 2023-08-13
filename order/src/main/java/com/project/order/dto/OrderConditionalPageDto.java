package com.project.order.dto;

import com.project.order.entity.OrderEntity;
import com.project.order.search.SearchCondition;
import com.project.order.search.SearchType;
import com.project.order.request.PageCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class OrderConditionalPageDto extends PageCondition<OrderEntity> {

    @ApiModelProperty(required = false)
    @SearchCondition(value = "id", condition = SearchType.EQUAL, qualifiedBy=OrderEntity.class)
    private Integer id;

    private Integer count;

    private Integer price;

    @ApiModelProperty(required = false)
    @SearchCondition(value = "productId", condition = SearchType.EQUAL, qualifiedBy=OrderEntity.class)
    private String productId;

}
