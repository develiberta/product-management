package com.project.product.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(
        name="tb_product",
        indexes={
                @Index(name="idx_product_01", columnList="name")
        })
public class ProductEntity extends ProductSuperEntity implements Serializable {
    @CreationTimestamp
    @Column(name="created_time")
    private Date createdTime;

    @UpdateTimestamp
    @Column(name="updated_time")
    private Date updatedTime;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="product")
    @PrimaryKeyJoinColumn
    InventoryEntity inventory;
}
