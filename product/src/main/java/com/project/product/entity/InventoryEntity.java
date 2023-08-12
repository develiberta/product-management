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
        name = "inventory",
        indexes = {
                @Index(name = "inventory_idx_01", columnList = "remaining")
        })
public class InventoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private Integer remaining;

    @OneToOne
    private ProductEntity product;
}
