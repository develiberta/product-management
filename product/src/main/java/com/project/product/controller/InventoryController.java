package com.project.product.controller;

import com.project.lib.response.ObjectResponse;
import com.project.lib.response.PageResponse;
import com.project.product.dto.inventory.InventoryDto;
import com.project.product.dto.product.ProductConditionalPageDto;
import com.project.product.dto.product.ProductDto;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductEntity;
import com.project.product.service.InventoryService;
import com.project.product.service.ProductService;
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
    public ResponseEntity<ObjectResponse<InventoryDto>> get(
            @PathVariable("id") InventoryEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(inventoryService.getRemaining(item)));
    }

}
