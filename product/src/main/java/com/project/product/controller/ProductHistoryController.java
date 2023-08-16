package com.project.product.controller;

import com.project.lib.response.ListResponse;
import com.project.lib.response.ObjectResponse;
import com.project.product.dto.ProductDto;
import com.project.product.dto.ProductHistoryDto;
import com.project.product.dto.ProductSuperDto;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.service.ProductHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "상품 이력 관리" })
@RestController
@RequestMapping(value = "/product_history")
public class ProductHistoryController {

    @Autowired
    ProductHistoryService producthistoryService;

    @ApiOperation(value="상품 이력 조회")
    @GetMapping(value="/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<ProductHistoryDto>> getProductHistories(
            @PathVariable("id") ProductEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ListResponse<>(producthistoryService.getProductHistoriesByProduct(item)));
    }

    @ApiOperation(value="상품 정보 조회")
    @GetMapping(value="/history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<ProductDto>> getProduct(
            @PathVariable("id") ProductHistoryEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(producthistoryService.getProductByProductHistory(item)));
    }

    @ApiOperation(value="상품 최신 이력 조회")
    @GetMapping(value="/recent_history/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<ProductHistoryDto>> getRecentProductHistory(
            @PathVariable("id") ProductEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(producthistoryService.getRecentProductHistory(item)));
    }

}
