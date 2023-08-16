package com.project.product.repository;

import com.project.product.entity.ProductHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistoryEntity, String>, JpaSpecificationExecutor<ProductHistoryEntity> {

    List<ProductHistoryEntity> findByProductId(String productId);

    ProductHistoryEntity findTopByProductIdOrderByCreatedTimeDesc(String productId);

}
