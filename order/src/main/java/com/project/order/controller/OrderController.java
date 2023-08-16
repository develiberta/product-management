package com.project.order.controller;

import com.project.lib.dto.OrderDto;
import com.project.lib.dto.OrderInsertDto;
import com.project.lib.dto.OrderUpdateDto;
import com.project.lib.response.ListResponse;
import com.project.lib.response.ObjectResponse;
import com.project.order.dto.OrderConditionalDto;
import com.project.order.entity.OrderEntity;
import com.project.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "주문 관리" })
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation(value="주문 상세 조회")
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> get(
            @PathVariable("id") OrderEntity item
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<OrderDto>(orderService.getOrder(item)));
    }

    @ApiOperation(value="주문 목록 조회")
    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<OrderDto>> page(
            @ModelAttribute OrderConditionalDto condition
    ) throws Exception {
        return ResponseEntity.ok().body(new ListResponse<>(orderService.getOrders(condition)));
    }
    
    @ApiOperation(value="주문 생성")
    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> create(
            @RequestBody OrderInsertDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(orderService.orderProduct(dtoNew)));
    }

    @ApiOperation(value="주문 갱신")
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<OrderDto>> update(
            @PathVariable("id") OrderEntity entityOld,
            @RequestBody OrderUpdateDto dtoNew
    ) throws Exception {
        return ResponseEntity.ok().body(new ObjectResponse<>(orderService.changeOrder(entityOld, dtoNew)));
    }

    @ApiOperation(value="주문 취소")
    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<String>> delete(
            @PathVariable("id") OrderEntity entity
    ) throws Exception {
        orderService.deleteOrder(entity);
        return ResponseEntity.ok().body(new ObjectResponse<>(entity.getId()));
    }

}
