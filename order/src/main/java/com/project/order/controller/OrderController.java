package com.project.order.controller;

import com.project.order.dto.OrderConditionalPageDto;
import com.project.order.dto.OrderDto;
import com.project.order.entity.OrderEntity;
import com.project.order.response.ObjectResponse;
import com.project.order.response.PageResponse;
import com.project.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "상품 관리" })
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value="상품 상세 조회")
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> get(
            @PathVariable("id") OrderEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<OrderDto>(orderService.getOrder(item)));
    }

    @ApiOperation(value="상품 목록 페이지 조회")
    @GetMapping(value="/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<OrderDto>> page(
            @ModelAttribute OrderConditionalPageDto condition
    ) throws Exception {
        return ResponseEntity.ok().body(new PageResponse<>(orderService.getOrders(condition)));
    }
    
    @ApiOperation(value="상품 등록")
    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> create(
            @RequestBody OrderDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(orderService.orderProduct(dtoNew)));
    }

    @ApiOperation(value="상품 갱신")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> update(
            @PathVariable("id") OrderEntity entityOld,
            @RequestBody OrderDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(orderService.changeOrder(entityOld, dtoNew)));
    }

    @ApiOperation(value="상품 삭제")
    @DeleteMapping(value="/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<String>> delete(
            @PathVariable("id") OrderEntity entity
    ) throws Exception {
        orderService.deleteOrder(entity);
        return ResponseEntity.ok().body(new ObjectResponse<>(entity.getId().toString()));
    }

}
