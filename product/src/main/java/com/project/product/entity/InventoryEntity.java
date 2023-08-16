package com.project.product.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name="tb_inventory",
        indexes={
                @Index(name = "idx_inventory_01", columnList = "remaining")
        })
public class InventoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name="id", referencedColumnName="id", foreignKey=@ForeignKey(name="fk_inventory_product_id"))
    private ProductEntity product;

    @Column(name="remaining")
    @ColumnDefault("0")
    private Integer remaining;

}
