package com.project.order.entity;

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
        name = "tb_order",
        indexes = {
                @Index(name = "idx_order_01", columnList = "product_id")
        })
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id")
    private String id;

    @Column(name="count")
    private Integer count;

    @Column(name="price")
    private Integer price;

    @Column(name="product_id")
    private String productId;
}
