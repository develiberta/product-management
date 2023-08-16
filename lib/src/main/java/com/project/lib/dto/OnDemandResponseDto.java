package com.project.lib.dto;


import com.project.lib.type.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnDemandResponseDto {

    Integer status;
    Result type;
    Object results;
    String message;
    Date timestamp;

}
