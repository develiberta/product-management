package com.project.order.service;

import com.project.order.dto.OrderConditionalPageDto;
import com.project.order.dto.OrderDto;
import com.project.order.entity.OrderEntity;
import com.project.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends BaseService {

    @Autowired
    OrderRepository orderRepository;

    public OrderDto getOrder(OrderEntity entity) throws Exception {
        OrderDto dto = modelMapper.map(entity, OrderDto.class);
        return dto;
    }

    public Page<OrderDto> getOrders(OrderConditionalPageDto condition) throws Exception {
        Specification<OrderEntity> spec = specBuilder.search(condition);
        return orderRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, OrderDto.class));
    }

    public OrderDto orderProduct(OrderDto dto) throws Exception {
        OrderEntity entity = orderRepository.save(modelMapper.map(dto, OrderEntity.class));
        return dto;
    }

    public OrderDto changeOrder(OrderEntity entityOld, OrderDto dtoNew) throws Exception {
        OrderEntity entitiyOld = orderRepository.save(modelMapper.map(dtoNew, OrderEntity.class));
        return dtoNew;
    }

    public void deleteOrder(OrderEntity entity) throws Exception {
        orderRepository.delete(entity);
    }

}
