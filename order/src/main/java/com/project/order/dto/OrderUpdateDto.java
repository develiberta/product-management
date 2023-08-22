package com.project.order.dto;

import com.project.lib.exception.DataException;
import com.project.lib.type.Origin;
import com.project.order.entity.OrderEntity;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@ToString
public class OrderUpdateDto {
    private Integer count;

    public void checkValid() {
        Optional.ofNullable(this.count).orElseThrow(() -> new DataException("주문 수량은 필수로 입력해야 합니다."));
        if (Integer.signum(this.count) <= 0) throw new DataException("주문 수량은 양수만 가능합니다.");
    }

    public OrderEntity updateBy(ModelMapper modelMapper, OrderEntity entityOld) {
        OrderEntity entity = modelMapper.map(this, OrderEntity.class);
        entity.setId(entityOld.getId());
        entity.setProductId(entityOld.getProductId());
        entity.setProductHistoryId(entityOld.getProductHistoryId());
        entity.setCreatedTime(entityOld.getCreatedTime());
        return entity;
    }
}
