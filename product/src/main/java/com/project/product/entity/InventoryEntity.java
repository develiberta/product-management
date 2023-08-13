package com.project.product.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(
        name = "tb_inventory",
        indexes = {
                @Index(name = "idx_inventory_01", columnList = "remaining")
        })
public class InventoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="remaining")
    private Integer remaining;

    @OneToOne
    @JoinColumn(name="product_id", foreignKey = @ForeignKey(name="fk_inventory_product_id"))
    private ProductEntity product;
}
