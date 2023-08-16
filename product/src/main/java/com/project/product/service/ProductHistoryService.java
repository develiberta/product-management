package com.project.product.service;

import com.project.lib.dto.InventoryDto;
import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.dto.ProductDto;
import com.project.product.dto.ProductHistoryDto;
import com.project.product.dto.ProductSuperDto;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
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

    public ProductDto getProductByProductHistory(ProductHistoryEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductEntity product = productRepository.findById(entity.getProductId()).orElse(null);
        ProductDto result = modelMapper.map(product, ProductDto.class);
        return result;
    }

    public List<ProductHistoryDto> getProductHistoriesByProduct(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        return modelMapper.map(
                productHistoryRepository.findByProductId(entity.getId()),
                new TypeToken<List<ProductHistoryDto>>(){}.getType()
        );
    }

    public ProductHistoryDto getRecentProductHistory(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductHistoryEntity history = productHistoryRepository.findTopByIdOrderByCreatedTimeDesc(entity.getId());
        ProductHistoryDto result = modelMapper.map(history, ProductHistoryDto.class);
        return result;
    }

}
