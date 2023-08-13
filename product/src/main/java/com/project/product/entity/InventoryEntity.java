package com.project.product.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id")
    private String id;

    @Column(name="remaining")
    private Integer remaining;

    @OneToOne
    @JoinColumn(name="product_id", foreignKey = @ForeignKey(name="fk_inventory_product_id"))
    private ProductEntity product;
}
