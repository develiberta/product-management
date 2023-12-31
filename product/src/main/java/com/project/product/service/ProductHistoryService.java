package com.project.product.service;

import com.project.lib.dto.ProductDto;
import com.project.lib.dto.ProductHistoryDto;
import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.repository.InventoryRepository;
import com.project.product.repository.ProductHistoryRepository;
import com.project.product.repository.ProductRepository;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductHistoryService extends BaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public ProductHistoryDto getProductHistory(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        ProductHistoryEntity entity = productHistoryRepository.findById(id).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        ProductHistoryDto result = modelMapper.map(entity, ProductHistoryDto.class);
        return result;
    }

    public ProductDto getProductByProductHistory(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        ProductHistoryEntity entity = productHistoryRepository.findById(id).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        ProductEntity product = productRepository.findById(entity.getProductId()).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        Optional.ofNullable(product).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductDto result = modelMapper.map(product, ProductDto.class);
        result.setRemaining(inventoryRepository.findByProductId(result.getId()).getRemaining());
        return result;
    }

    public List<ProductHistoryDto> getProductHistoriesByProduct(String id) throws Exception {
        return modelMapper.map(
                productHistoryRepository.findByProductId(id),
                new TypeToken<List<ProductHistoryDto>>(){}.getType()
        );
    }

    public ProductHistoryDto getRecentProductHistory(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductEntity entity = productRepository.findById(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductHistoryEntity history = productHistoryRepository.findTopByProductIdOrderByCreatedTimeDesc(entity.getId());
        Optional.ofNullable(history).orElseThrow(() -> new DataException("상품 이력이 존재하지 않습니다."));
        ProductHistoryDto result = modelMapper.map(history, ProductHistoryDto.class);
        return result;
    }

}
