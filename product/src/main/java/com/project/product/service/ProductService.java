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

import java.util.Date;
import java.util.Optional;

@Service
public class ProductService extends BaseService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    public ProductDto getProduct(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductEntity entity = productRepository.findById(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductDto result = modelMapper.map(entity, ProductDto.class);
        InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElseThrow(() -> new DataException("상품의 재고가 정상적으로 입력되지 않았습니다."));
        result.setRemaining(inventory.getRemaining());
        return result;
    }

    public Page<ProductDto> getProductsByPagination(ProductConditionalPageDto condition) throws Exception {
        Specification<ProductEntity> spec = specBuilder.search(condition);
        Page<ProductDto> results = productRepository.findAll(spec, condition.makePageable()).map(item -> modelMapper.map(item, ProductDto.class));
        for (ProductDto result : results) {
            InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElse(null);
            if (inventory == null) {
                logger.warn("tb_inventory에 상품의 재고가 정상적으로 입력되지 않았습니다. (id={})", result.getId());
            } else {
                result.setRemaining(inventory.getRemaining());
            }
        }
        return results;
    }

    @Transactional
    public ProductDto addProduct(ProductUpsertDto dto) throws Exception {
        ProductEntity entity = productRepository.save(modelMapper.map(dto, ProductEntity.class));
        /* [확인 필요] createdTime, updatedTime 이 db에서는 정상적으로 보이지만 entity에서는 null임을 확인 */
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
    public ProductDto updateProduct(String id, ProductUpsertDto dtoNew) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductEntity entityOld = productRepository.findById(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
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
        InventoryEntity inventory = inventoryRepository.findById(result.getId()).orElseThrow(() -> new DataException("상품의 재고가 정상적으로 입력되지 않았습니다."));
        result.setRemaining(inventory.getRemaining());
        return result;
    }

    @Transactional
    public String deleteProduct(String id) throws Exception {
        Optional.ofNullable(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        ProductEntity entity = productRepository.findById(id).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
//        inventoryRepository.deleteById(entity.getId());
        String entityId = entity.getId();
        productRepository.delete(entity);
        ProductHistoryEntity history = modelMapper.map(entity, ProductHistoryEntity.class);
        history.setProductId(entity.getId());
        history.setAction(Action.deleted);
        productHistoryRepository.save(history);
        return entityId;
    }

}
