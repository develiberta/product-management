package com.project.product.controller;

import com.project.lib.response.ObjectResponse;
import com.project.lib.response.PageResponse;
import com.project.product.dto.ProductConditionalPageDto;
import com.project.product.dto.ProductDto;
import com.project.product.dto.ProductUpsertDto;
import com.project.product.entity.ProductEntity;
import com.project.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<ProductDto>> get(
            @PathVariable("id") ProductEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<ProductDto>(productService.getProduct(item)));
    }

    @ApiOperation(value="상품 목록 조회 (페이지)")
    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<ProductDto>> page(
            @ModelAttribute ProductConditionalPageDto condition
    ) throws Exception {
        return ResponseEntity.ok().body(new PageResponse<>(productService.getProductsByPagination(condition)));
    }
    
    @ApiOperation(value="상품 등록")
    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<ProductDto>> create(
            @RequestBody ProductUpsertDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(productService.addProduct(dtoNew)));
    }

    @ApiOperation(value="상품 갱신")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<ProductDto>> update(
            @PathVariable("id") ProductEntity entityOld,
            @RequestBody ProductUpsertDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(productService.updateProduct(entityOld, dtoNew)));
    }

    @ApiOperation(value="상품 삭제")
    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<String>> delete(
            @PathVariable("id") ProductEntity entity
    ) throws Exception {
        productService.deleteProduct(entity);
        return ResponseEntity.ok().body(new ObjectResponse<>(entity.getId().toString()));
    }

}
