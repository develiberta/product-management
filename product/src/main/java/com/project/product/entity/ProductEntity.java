package com.project.product.entity;

import com.project.product.type.Origin;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(
        name = "tb_product",
        indexes = {
                @Index(name = "idx_product_01", columnList = "name")
        })
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="origin")
    private Origin origin;

    @Column(name="price")
    private Integer price;

    @Column(name="cost")
    private Integer cost;

    @Column(name="image")
    private String image;

    @Column(name="description")
    private String description;
}
