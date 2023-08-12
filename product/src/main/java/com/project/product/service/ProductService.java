package com.project.product.service;

import com.project.product.dto.product.ProductConditionalPageDto;
import com.project.product.dto.product.ProductDto;
import com.project.product.entity.ProductEntity;
import com.project.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends BaseService {

    @Autowired
    ProductRepository productRepository;

    public ProductDto getProduct(ProductEntity entity) throws Exception {
        ProductDto dto = modelMapper.map(entity, ProductDto.class);
        return dto;
    }

    public Page<ProductDto> getProductsByPagination(ProductConditionalPageDto condition) throws Exception {
        Specification<ProductEntity> spec = specBuilder.search(condition);
        return productRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, ProductDto.class));
    }

    public ProductDto addProduct(ProductDto dto) throws Exception {
        ProductEntity entity = productRepository.save(modelMapper.map(dto, ProductEntity.class));
        return dto;
    }

    public ProductDto updateProduct(ProductEntity entityOld, ProductDto dtoNew) throws Exception {
        ProductEntity entitiyOld = productRepository.save(modelMapper.map(dtoNew, ProductEntity.class));
        return dtoNew;
    }

    public void deleteProduct(ProductEntity entity) throws Exception {
        productRepository.delete(entity);
    }

}
