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

    public OrderDto getOrder(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        Optional.ofNullable(entity.getProductHistoryId()).orElseThrow(() -> new DataException("주문한 상품 이력이 존재하지 않습니다."));
        ProductHistoryDto history = getProductHistory(entity.getProductHistoryId());
        Optional.ofNullable(history).orElseThrow(() -> new DataException("주문한 상품 이력이 존재하지 않습니다."));
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
    public OrderDto orderProduct(OrderInsertDto dto) throws Exception {
        dto.checkValid();
        if (Integer.signum(dto.getCount()) <= 0) throw new DataException("주문 수량은 양수만 가능합니다.");
        InventoryDto inventory = getRemaining(dto.getProductId());
        Integer remaining = inventory.getRemaining() - dto.getCount();
        if (remaining < 0) throw new ServiceException("재고가 충분하지 않습니다.");
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
    public OrderDto changeOrder(String id, OrderUpdateDto dtoNew) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        dtoNew.checkValid();
        OrderEntity entityOld = orderRepository.findById(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        if (Integer.signum(dtoNew.getCount()) <= 0) throw new DataException("주문 수량은 양수만 가능합니다.");
        InventoryDto inventory = getRemaining(entityOld.getProductId());
        Integer remaining = inventory.getRemaining() - (dtoNew.getCount() - entityOld.getCount());
        if (remaining < 0) throw new ServiceException("재고가 충분하지 않습니다.");
        updateRemaining(entityOld.getProductId(), new InventoryUpsertDto(remaining));
        OrderEntity entity = modelMapper.map(dtoNew, OrderEntity.class);
        entity.setId(entityOld.getId());
        entity.setProductId(entityOld.getProductId());
        ProductHistoryDto history = getRecentProductHistory(entity.getProductId());
        if (!history.getId().equals(entityOld.getProductHistoryId())) throw new ServiceException("상품 정보가 변경되었습니다. 취소 후 재주문 부탁드립니다.");
        entity.setProductHistoryId(entityOld.getProductHistoryId());
        entity.setCreatedTime(entityOld.getCreatedTime());
        entity = orderRepository.save(entity);
        OrderDto result = modelMapper.map(entity, OrderDto.class);
        result.setProductInfo(history.getName(), history.getOrigin(), history.getPrice(), history.getCost(), history.getImage(), history.getDescription());
        return result;
    }

    @Transactional
    public String deleteOrder(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new DataException("주문이 존재하지 않습니다."));
        String entityId = entity.getId();
        InventoryDto inventory = getRemaining(entity.getProductId());
        Integer remaining = inventory.getRemaining() + entity.getCount();
        updateRemaining(entity.getProductId(), new InventoryUpsertDto(remaining));
        orderRepository.delete(entity);
        return entityId;
    }

    private boolean isEnoughRemaining(String productId, Integer count) throws Exception {
        InventoryDto result = getRemaining(productId);
        return result.getRemaining() >= count;
    }

    private InventoryDto getRemaining(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/inventory/" + productId, null);
        InventoryDto result = jacksonMapper.mapToClass((Map) response.getResults(), InventoryDto.class);
        Optional.ofNullable(result).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        return result;
    }

    private InventoryDto updateRemaining(String productId, InventoryUpsertDto dtoNew) throws Exception {
        dtoNew.checkValid();
        OnDemandResponseDto response = onDemandService.demandToServerByPut("/inventory/" + productId, null, dtoNew);
        InventoryDto result = jacksonMapper.mapToClass((Map) response.getResults(), InventoryDto.class);
        Optional.ofNullable(result).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        return result;
    }

    private ProductHistoryDto getProductHistory(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/product_history/" + productId, null);
        ProductHistoryDto result = jacksonMapper.mapToClass((Map) response.getResults(), ProductHistoryDto.class);
        Optional.ofNullable(result).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        return result;
    }

    private ProductHistoryDto getRecentProductHistory(String productId) throws Exception {
        OnDemandResponseDto response = onDemandService.demandToServerByGet("/product_history/product/" + productId + "/recent_history", null);
        ProductHistoryDto result = jacksonMapper.mapToClass((Map) response.getResults(), ProductHistoryDto.class);
        Optional.ofNullable(result).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        return result;
    }

}
