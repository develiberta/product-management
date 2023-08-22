package com.project.order.dto;

import com.project.lib.exception.DataException;
import com.project.order.entity.OrderEntity;
import lombok.Data;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Data
@ToString
public class OrderInsertDto {
    private Integer count;

    private String productId;

    public void checkValid() {
        Optional.ofNullable(this.productId).orElseThrow(() -> new DataException("주문 상품은 필수로 입력해야 합니다."));
        Optional.ofNullable(this.count).orElseThrow(() -> new DataException("주문 수량은 필수로 입력해야 합니다."));
        if (Integer.signum(this.count) <= 0) throw new DataException("주문 수량은 양수만 가능합니다.");
    }

    public OrderEntity createBy(ModelMapper modelMapper) {
        checkValid();
        return modelMapper.map(this, OrderEntity.class);
    }
}
