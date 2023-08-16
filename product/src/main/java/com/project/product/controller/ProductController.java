package com.project.product.controller;

import com.project.lib.dto.ProductDto;
import com.project.lib.dto.ProductUpsertDto;
import com.project.lib.response.ErrorResponse;
import com.project.lib.response.ObjectResponse;
import com.project.lib.response.PageResponse;
import com.project.product.dto.ProductConditionalPageDto;
import com.project.product.entity.ProductEntity;
import com.project.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "상품 관리" })
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation(value="상품 상세 조회")
    @GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(productService.getProduct(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 목록 조회 (페이지)")
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity page(
            @ModelAttribute ProductConditionalPageDto condition
    ) {
        try {
            return ResponseEntity.ok().body(new PageResponse<>(productService.getProductsByPagination(condition)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }
    
    @ApiOperation(value="상품 등록")
    @PostMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            @RequestBody ProductUpsertDto dtoNew
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(productService.addProduct(dtoNew)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 갱신")
    @PutMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable("id") String id,
            @RequestBody ProductUpsertDto dtoNew
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(productService.updateProduct(id, dtoNew)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="상품 삭제")
    @DeleteMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(productService.deleteProduct(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

}
