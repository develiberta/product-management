package com.project.order.dto;


import com.project.order.type.OnDemandResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnDemandResponseDto {

    OnDemandResponseType result;
    Object responseBody;
    String message;

}
