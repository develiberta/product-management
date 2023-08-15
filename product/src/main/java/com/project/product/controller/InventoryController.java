package com.project.product.controller;

import com.project.lib.response.ObjectResponse;
import com.project.product.dto.InventoryDto;
import com.project.product.dto.InventoryUpsertDto;
import com.project.product.dto.ProductDto;
import com.project.product.dto.ProductUpsertDto;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductEntity;
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

    @ApiOperation(value="재고 조회")
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<InventoryDto>> getRemaining(
            @PathVariable("id") InventoryEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.getRemaining(item)));
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
