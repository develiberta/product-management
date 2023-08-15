package com.project.product.service;

import com.project.lib.exception.DataException;
import com.project.lib.service.BaseService;
import com.project.product.dto.InventoryDto;
import com.project.product.dto.InventoryUpsertDto;
import com.project.product.entity.InventoryEntity;
import com.project.product.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService extends BaseService {

    @Autowired
    InventoryRepository inventoryRepository;

    public InventoryDto getRemaining(InventoryEntity entity) {
        Optional.ofNullable(entity).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        InventoryDto result  = modelMapper.map(inventoryRepository.findByProductId(entity.getId()), InventoryDto.class);
        return result;
    }

    public InventoryDto updateRemaining(InventoryEntity entityOld, InventoryUpsertDto dtoNew) {
        Optional.ofNullable(entityOld).orElseThrow(() -> new DataException("입력 자료가 존재하지 않습니다."));
        entityOld.setRemaining(dtoNew.getRemaining());
        InventoryDto result = modelMapper.map(inventoryRepository.save(entityOld), InventoryDto.class);
        return result;
    }

}
