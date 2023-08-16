package com.project.lib.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InventoryUpsertDto {
    private Integer remaining;

    public InventoryUpsertDto() {

    }
    public InventoryUpsertDto(Integer remaining) {
        this.setRemaining(remaining);
    }

//    public void plusRemaining(Integer delta) {
//        this.setRemaining(remaining + delta);
//    }
}
