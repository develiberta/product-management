package com.project.lib.dto;

import com.project.lib.exception.DataException;
import lombok.Data;
import lombok.ToString;

import java.util.Optional;

@Data
@ToString
public class InventoryUpsertDto {
    private Integer remaining;

    public InventoryUpsertDto() {

    }
    public InventoryUpsertDto(Integer remaining) {
        this.setRemaining(remaining);
    }

    public void checkValid() {
        Optional.ofNullable(this.remaining).orElseThrow(() -> new DataException("재고 수량은 필수로 입력해야 합니다."));
        if (Integer.signum(this.remaining) < 0) throw new DataException("재고 수량은 0 또는 양수만 가능합니다.");
    }
}
