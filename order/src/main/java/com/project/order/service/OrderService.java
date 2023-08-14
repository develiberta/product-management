package com.project.order.service;

import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.order.dto.OrderConditionalPageDto;
import com.project.order.dto.OrderDto;
import com.project.order.entity.OrderEntity;
import com.project.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService extends BaseService {

    @Autowired
    OrderRepository orderRepository;

    public OrderDto getOrder(OrderEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        OrderDto dto = modelMapper.map(entity, OrderDto.class);
        return dto;
    }

    public Page<OrderDto> getOrders(OrderConditionalPageDto condition) throws Exception {
        Specification<OrderEntity> spec = specBuilder.search(condition);
        return orderRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, OrderDto.class));
    }

    public OrderEntity orderProduct(OrderDto dto) throws Exception {
        OrderEntity entity = orderRepository.save(modelMapper.map(dto, OrderEntity.class));
        return entity;
    }

    public OrderEntity changeOrder(OrderEntity entityOld, OrderDto dtoNew) throws Exception {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        OrderEntity entity = orderRepository.save(modelMapper.map(dtoNew, OrderEntity.class));
        return entity;
    }

    public void deleteOrder(OrderEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        orderRepository.delete(entity);
    }

}
