package com.project.product.service;

import com.project.lib.service.BaseService;
import com.project.product.dto.inventory.InventoryDto;
import com.project.product.entity.InventoryEntity;
import com.project.product.entity.ProductEntity;
import com.project.product.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService extends BaseService {

    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryDto getRemaining(InventoryEntity entity) {
        return modelMapper.map(inventoryRepository.findByProductId(entity.getId()), InventoryDto.class);
    }

}
