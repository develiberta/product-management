package com.project.product.controller;

import com.project.lib.dto.ProductDto;
import com.project.lib.dto.ProductHistoryDto;
import com.project.lib.response.ErrorResponse;
import com.project.lib.response.ListResponse;
import com.project.lib.response.ObjectResponse;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.service.ProductHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductHistories(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(producthistoryService.getProductHistory(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 코드로 이력 조회")
    @GetMapping(value="/product/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductHistoriesByProduct(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ListResponse<>(producthistoryService.getProductHistoriesByProduct(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 이력 코드로 상품 조회")
    @GetMapping(value="/history/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductByProductHistory(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(producthistoryService.getProductByProductHistory(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 코드로 상품 최신 이력 조회")
    @GetMapping(value="/product/{id}/recent_history", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecentProductHistory(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(producthistoryService.getRecentProductHistory(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

}
