package com.project.lib.dto;

import com.project.lib.exception.DataException;
import com.project.lib.type.Origin;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;

@Data
@ToString
public class ProductUpsertDto {
    private String name;

    @Enumerated(EnumType.STRING)
    private Origin origin;

    private Integer price;

    private Integer cost;

    private String image;

    private String description;
    
    public void checkValid() {
        if (StringUtils.isAllEmpty(this.name)) new DataException("상품 이름은 필수로 입력해야 합니다.");
        Optional.ofNullable(this.origin).orElseThrow(() -> new DataException("원산지는 필수로 입력해야 합니다."));
        Optional.ofNullable(this.price).orElseThrow(() -> new DataException("상품 가격은 필수로 입력해야 합니다."));
        if (Integer.signum(this.price) < 0) throw new DataException("상품 가격은 0 또는 양수만 가능합니다.");
        Optional.ofNullable(this.cost).orElseThrow(() -> new DataException("상품 원가는 필수로 입력해야 합니다."));
        if (Integer.signum(this.cost) < 0) throw new DataException("상품 원가는 0 또는 양수만 가능합니다.");
    }
}
