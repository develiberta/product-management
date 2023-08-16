package com.project.order.service;

import com.project.lib.dto.*;
import com.project.lib.exception.DataException;
import com.project.lib.exception.ServiceException;
import com.project.lib.service.BaseService;
import com.project.order.dto.OrderConditionalDto;
import com.project.order.entity.OrderEntity;
import com.project.order.repository.OrderRepository;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        ProductHistoryDto history = getProductHistory(entity.getProductHistoryId());
        OrderDto result = modelMapper.map(entity, OrderDto.class);
        result.setProductInfo(history.getName(), history.getOrigin(), history.getPrice(), history.getCost(), history.getImage(), history.getDescription());
        return result;
    }

    public List<OrderDto> getOrders(OrderConditionalDto condition) throws Exception {
        Specification<OrderEntity> spec = specBuilder.search(condition);
        List<OrderDto> results = modelMapper.map(orderRepository.findAll(spec),
                new TypeToken<List<OrderDto>>(){}.getType()
        );
        for (OrderDto result : results) {
            ProductHistoryDto history = getProductHistory(result.getProductHistoryId());
            result.setProductInfo(history.getName(), history.getOrigin(), history.getPrice(), history.getCost(), history.getImage(), history.getDescription());
        }

        return results;
    }

    @Transactional
    public OrderDto orderProduct(OrderUpsertDto dto) throws Exception {
        InventoryDto inventory = getRemaining(dto.getProductId());
        Integer remaining = inventory.getRemaining() - dto.getCount();
        if (remaining < 0) throw new DataException("재고가 충분하지 않습니다.");
        updateRemaining(dto.getProductId(), new InventoryUpsertDto(remaining));
        OrderEntity entity = modelMapper.map(dto, OrderEntity.class);
        ProductHistoryDto history = getRecentProductHistory(entity.getProductId());
        entity.setProductHistoryId(history.getId());
        entity = orderRepository.save(entity);
        OrderDto result = modelMapper.map(entity, OrderDto.class);
        result.setProductInfo(history.getName(), history.getOrigin(), history.getPrice(), history.getCost(), history.getImage(), history.getDescription());
        return result;
    }

    @Transactional
    public OrderDto changeOrder(OrderEntity entityOld, OrderUpsertDto dtoNew) throws Exception {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        InventoryDto inventory = getRemaining(dtoNew.getProductId());
        Integer remaining = inventory.getRemaining() - (dtoNew.getCount() - entityOld.getCount());
        if (remaining < 0) throw new DataException("재고가 충분하지 않습니다.");
        updateRemaining(dtoNew.getProductId(), new InventoryUpsertDto(remaining));
        OrderEntity entity = modelMapper.map(dtoNew, OrderEntity.class);
        entity.setId(entityOld.getId());
        ProductHistoryDto history = getRecentProductHistory(entity.getProductId());
        if (!history.getId().equals(entityOld.getProductHistoryId())) throw new ServiceException("상품 정보가 변경되었습니다. 취소 후 재주문 부탁드립니다.");
        entity.setProductHistoryId(entityOld.getProductHistoryId());
        entity = orderRepository.save(entity);
        OrderDto result = modelMapper.map(entity, OrderDto.class);
        result.setProductInfo(history.getName(), history.getOrigin(), history.getPrice(), history.getCost(), history.getImage(), history.getDescription());
        return result;
    }

    @Transactional
    public void deleteOrder(OrderEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        updateRemaining(entity.getProductId(), new InventoryUpsertDto(-entity.getCount()));
        orderRepository.delete(entity);
    }

    private boolean isEnoughRemaining(String productId, Integer count) throws Exception {
        InventoryDto result = getRemaining(productId);
        return result.getRemaining() >= count;
    }

    private InventoryDto getRemaining(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/inventory/" + productId, null);
        return jacksonMapper.mapToClass((Map) response.getResults(), InventoryDto.class);
    }

    private InventoryDto updateRemaining(String productId, InventoryUpsertDto request) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByPut("/inventory/" + productId, null, request);
        return jacksonMapper.mapToClass((Map) response.getResults(), InventoryDto.class);
    }

    private ProductHistoryDto getProductHistory(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/product_history/" + productId, null);
        return jacksonMapper.mapToClass((Map) response.getResults(), ProductHistoryDto.class);
    }

    private ProductHistoryDto getRecentProductHistory(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/product_history/recent_history/" + productId, null);
        return jacksonMapper.mapToClass((Map) response.getResults(), ProductHistoryDto.class);
    }

    private ProductDto getProduct(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/product/" + productId, null);
        return jacksonMapper.mapToClass((Map) response.getResults(), ProductDto.class);
    }

}
