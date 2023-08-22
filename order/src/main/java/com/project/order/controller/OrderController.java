package com.project.order.controller;

import com.project.order.dto.OrderInsertDto;
import com.project.order.dto.OrderUpdateDto;
import com.project.lib.response.ErrorResponse;
import com.project.lib.response.ListResponse;
import com.project.lib.response.ObjectResponse;
import com.project.order.dto.OrderConditionalDto;
import com.project.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(orderService.getOrder(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="주문 목록 조회")
    @GetMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity page(
            @ModelAttribute OrderConditionalDto condition
    ) {
        try {
            return ResponseEntity.ok().body(new ListResponse<>(orderService.getOrders(condition)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }
    
    @ApiOperation(value="주문 생성")
    @PostMapping(value="", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(
            @RequestBody OrderInsertDto dtoNew
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(orderService.orderProduct(dtoNew)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="주문 갱신")
    @PutMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(
            @PathVariable("id") String id,
            @RequestBody OrderUpdateDto dtoNew
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(orderService.changeOrder(id, dtoNew)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

    @ApiOperation(value="주문 취소")
    @DeleteMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(
            @PathVariable("id") String id
    ) {
        try {
            return ResponseEntity.ok().body(new ObjectResponse<>(orderService.deleteOrder(id)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e));
        }
    }

}
