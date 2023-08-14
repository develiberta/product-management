package com.project.product.entity;

import lombok.*;
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
        name = "tb_product_history",
        indexes = {
                @Index(name = "idx_product_history_01", columnList = "name"),
                @Index(name = "idx_product_history_02", columnList = "updated_time")
        })
public class ProductHistoryEntity extends ProductSuperEntity implements Serializable {
    @UpdateTimestamp
    @Column(name="updated_time")
    Date updatedTime;

    @ManyToOne
    @JoinColumn(name="product_id", foreignKey = @ForeignKey(name="fk_product_history_product_id"))
    ProductEntity product;
}
