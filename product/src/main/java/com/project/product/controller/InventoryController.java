package com.project.product.controller;

import com.project.lib.dto.InventoryDto;
import com.project.lib.dto.InventoryUpsertDto;
import com.project.lib.response.ErrorResponse;
import com.project.lib.response.ObjectResponse;
import com.project.product.entity.InventoryEntity;
import com.project.product.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "재고 관리" })
@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @ApiOperation(value="재고 조회")
    @GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRemainingByProduct(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.getRemainingByProduct(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="재고 수정")
    @PutMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable("id") String id,
            @RequestBody InventoryUpsertDto dtoNew
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.updateRemaining(id, dtoNew)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

}
