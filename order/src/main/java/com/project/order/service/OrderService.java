package com.project.order.service;

import com.project.lib.dto.InventoryDto;
import com.project.lib.dto.InventoryUpsertDto;
import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.order.dto.OnDemandResponseDto;
import com.project.order.dto.OrderConditionalPageDto;
import com.project.order.dto.OrderDto;
import com.project.order.dto.OrderUpsertDto;
import com.project.order.entity.OrderEntity;
import com.project.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderService extends BaseService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OnDemandService onDemandService;

    public OrderDto getOrder(OrderEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        OrderDto dto = modelMapper.map(entity, OrderDto.class);
        return dto;
    }

    public Page<OrderDto> getOrders(OrderConditionalPageDto condition) throws Exception {
        Specification<OrderEntity> spec = specBuilder.search(condition);
        return orderRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, OrderDto.class));
    }

    public OrderDto orderProduct(OrderUpsertDto dto) throws Exception {
        if (isEnoughRemaining(dto.getProductId(), dto.getCount()) == false) throw new DataException("재고가 충분하지 않습니다.");
        InventoryUpsertDto inventory = new InventoryUpsertDto();
        inventory.setRemaining(inventory.getRemaining() - dto.getCount());
        updateRemaining(dto.getProductId(), inventory);
        OrderEntity entity = orderRepository.save(modelMapper.map(dto, OrderEntity.class));
        return modelMapper.map(entity, OrderDto.class);
    }

    public OrderDto changeOrder(OrderEntity entityOld, OrderUpsertDto dtoNew) throws Exception {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        if (isEnoughRemaining(dtoNew.getProductId(), dtoNew.getCount()) == false) throw new DataException("재고가 충분하지 않습니다.");
        OrderEntity entity = orderRepository.save(modelMapper.map(dtoNew, OrderEntity.class));
        return modelMapper.map(entity, OrderDto.class);
    }

    public void deleteOrder(OrderEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        orderRepository.delete(entity);
    }

    private boolean isEnoughRemaining(String id, Integer count) throws Exception {
        InventoryDto result = getRemaining(id);
        return result.getRemaining() >= count ? true : false;
    }

    private InventoryDto getRemaining(String id) throws Exception {
        Map<String, Object> queryParamMap = Map.of(
                "id", id
        );

        OnDemandResponseDto result = onDemandService.demandToServerByGet("/inventory/" + id, queryParamMap);
        return (InventoryDto) result.getResponseBody();
    }

    private InventoryDto updateRemaining(String id, InventoryUpsertDto request) throws Exception {
        Map<String, Object> queryParamMap = Map.of(
                "id", id
        );
        OnDemandResponseDto result = onDemandService.demandToServerByPut("/inventory/" + id, queryParamMap, request);
        return (InventoryDto) result.getResponseBody();
    }

    private OrderDto getOrder(String id) {
        return null;
    }

    private OrderDto getOrders() {
        return null;
    }

}
