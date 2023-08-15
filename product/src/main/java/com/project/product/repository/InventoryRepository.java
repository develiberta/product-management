package com.project.product.repository;

import com.project.product.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String>, JpaSpecificationExecutor<InventoryEntity> {
    InventoryEntity findByProductId(String productId);
}
