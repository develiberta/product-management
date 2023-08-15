package com.project.product.service;

import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.dto.product.ProductConditionalPageDto;
import com.project.product.dto.product.ProductDto;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.repository.ProductHistoryRepository;
import com.project.product.repository.ProductRepository;
import com.project.product.type.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends BaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    public ProductDto getProduct(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductDto dto = modelMapper.map(entity, ProductDto.class);
        return dto;
    }

    public Page<ProductDto> getProductsByPagination(ProductConditionalPageDto condition) throws Exception {
        Specification<ProductEntity> spec = specBuilder.search(condition);
        return productRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, ProductDto.class));
    }

    public ProductEntity addProduct(ProductDto dto) throws Exception {
        ProductEntity entity = productRepository.save(modelMapper.map(dto, ProductEntity.class));
        ProductHistoryEntity history = modelMapper.map(dto, ProductHistoryEntity.class);
        history.setAction(Action.created);
        productHistoryRepository.save(history);
        return entity;
    }

    public ProductEntity updateProduct(ProductEntity entityOld, ProductDto dtoNew) throws Exception {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductEntity entitiy = productRepository.save(modelMapper.map(dtoNew, ProductEntity.class));
        ProductHistoryEntity history = modelMapper.map(dtoNew, ProductHistoryEntity.class);
        history.setAction(Action.updated);
        productHistoryRepository.save(history);
        return entitiy;
    }

    public void deleteProduct(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        productRepository.delete(entity);
        ProductHistoryEntity history = modelMapper.map(entity, ProductHistoryEntity.class);
        history.setAction(Action.deleted);
        productHistoryRepository.save(history);
    }

}
