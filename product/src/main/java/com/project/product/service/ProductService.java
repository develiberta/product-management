package com.project.product.service;

import com.project.lib.dto.ProductDto;
import com.project.lib.dto.ProductUpsertDto;
import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.dto.ProductConditionalPageDto;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.repository.InventoryRepository;
import com.project.product.repository.ProductHistoryRepository;
import com.project.product.repository.ProductRepository;
import com.project.product.type.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService extends BaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public ProductDto getProduct(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductDto result = modelMapper.map(entity, ProductDto.class);
        InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElse(null);
        result.setRemaining(inventory.getRemaining());
        return result;
    }

    public Page<ProductDto> getProductsByPagination(ProductConditionalPageDto condition) throws Exception {
        Specification<ProductEntity> spec = specBuilder.search(condition);
        Page<ProductDto> results = productRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, ProductDto.class));
        for (ProductDto result : results) {
            InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElse(null);
            result.setRemaining(inventory.getRemaining());
        }
        return results;
    }

    @Transactional
    public ProductDto addProduct(ProductUpsertDto dto) throws Exception {
        ProductEntity entity = productRepository.save(modelMapper.map(dto, ProductEntity.class));
        ProductHistoryEntity history = modelMapper.map(dto, ProductHistoryEntity.class);
        history.setProductId(entity.getId());
        history.setAction(Action.created);
        productHistoryRepository.save(history);
        InventoryEntity inventory = new InventoryEntity();
        inventory.setProduct(entity);
        inventory.setRemaining(0);
        inventory = inventoryRepository.save(inventory);
        ProductDto result = modelMapper.map(entity, ProductDto.class);
        result.setRemaining(inventory.getRemaining());
        return result;
    }

    @Transactional
    public ProductDto updateProduct(ProductEntity entityOld, ProductUpsertDto dtoNew) throws Exception {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        ProductEntity entity = modelMapper.map(dtoNew, ProductEntity.class);
        entity.setId(entityOld.getId());
        entity.setCreatedTime(entityOld.getCreatedTime());
        entity.setInventory(entityOld.getInventory());
        entity = productRepository.save(entity);
        ProductHistoryEntity history = modelMapper.map(dtoNew, ProductHistoryEntity.class);
        history.setProductId(entity.getId());
        history.setAction(Action.updated);
        productHistoryRepository.save(history);
        ProductDto result = modelMapper.map(entity, ProductDto.class);
        InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElse(null);
        result.setRemaining(inventory.getRemaining());
        return result;
    }

    @Transactional
    public void deleteProduct(ProductEntity entity) throws Exception {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
//        inventoryRepository.deleteById(entity.getId());
        productRepository.delete(entity);
        ProductHistoryEntity history = modelMapper.map(entity, ProductHistoryEntity.class);
        history.setProductId(entity.getId());
        history.setAction(Action.deleted);
        productHistoryRepository.save(history);
    }

}
