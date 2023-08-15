package com.project.product.repository;

import com.project.product.entity.ProductHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistoryEntity, String>, JpaSpecificationExecutor<ProductHistoryEntity> {

}