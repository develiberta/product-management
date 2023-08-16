package com.project.lib.dto;

import com.project.lib.exception.DataException;
import com.project.lib.type.Origin;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Data
@ToString
public class OrderUpdateDto {
    private Integer count;

    public void checkValid() {
        Optional.ofNullable(this.count).orElseThrow(() -> new DataException("주문 수량은 필수로 입력해야 합니다."));
        if (Integer.signum(this.count) <= 0) throw new DataException("주문 수량은 양수만 가능합니다.");
    }
}
