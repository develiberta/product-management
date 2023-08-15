package com.project.product.service;

import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.entity.InventoryEntity;
import com.project.product.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService extends BaseService {

    @Autowired
    InventoryRepository inventoryRepository;

    public Integer getRemaining(InventoryEntity entity) {
        return modelMapper.map(inventoryRepository.findByProductId(entity.getId()), Integer.class);
    }

    public Integer updateRemaining(InventoryEntity entityOld, Integer remaining) {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        InventoryEntity entity = new InventoryEntity();
        entity.setRemaining(remaining);
        inventoryRepository.save(entity);
        return remaining;
    }

}
