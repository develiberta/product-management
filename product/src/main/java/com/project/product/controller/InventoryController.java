package com.project.product.controller;

import com.project.lib.dto.InventoryDto;
import com.project.lib.dto.InventoryUpsertDto;
import com.project.lib.response.ObjectResponse;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "재고 관리" })
@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @ApiOperation(value="재고 조회 (제품 코드로 검색)")
    @GetMapping(value="/{id}/by_product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<InventoryDto>> getRemainingByProduct(
            @PathVariable("id") InventoryEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.getRemainingByProduct(item)));
    }

    @ApiOperation(value="재고 조회 (제품 이력 코드로 검색)")
    @GetMapping(value="/{id}/by_product_history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<InventoryDto>> getRemainingByProductHistory(
            @PathVariable("id") ProductHistoryEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.getRemainingByProductHistory(item)));
    }

    @ApiOperation(value="재고 수정")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<InventoryDto>> update(
            @PathVariable("id") InventoryEntity entityOld,
            @RequestBody InventoryUpsertDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.updateRemaining(entityOld, dtoNew)));
    }

}
