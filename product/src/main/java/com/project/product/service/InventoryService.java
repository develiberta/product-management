package com.project.product.service;

import com.project.lib.dto.InventoryDto;
import com.project.lib.dto.InventoryUpsertDto;
import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductHistoryEntity;
import com.project.product.repository.InventoryRepository;
import com.project.product.repository.ProductHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService extends BaseService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ProductHistoryRepository productHistoryRepository;

    public InventoryDto getRemainingByProduct(InventoryEntity entity) {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        return modelMapper.map(entity, InventoryDto.class);
    }

    public InventoryDto updateRemaining(InventoryEntity entityOld, InventoryUpsertDto dtoNew) {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("상품이 존재하지 않습니다."));
        InventoryEntity entity = inventoryRepository.findById(entityOld.getId()).orElse(null);
        entity.setRemaining(dtoNew.getRemaining());
        InventoryDto result = modelMapper.map(inventoryRepository.save(entityOld), InventoryDto.class);
        return result;
    }

}
