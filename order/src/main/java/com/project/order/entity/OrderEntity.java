package com.project.order.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
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
        name="tb_order",
        indexes={
                @Index(name="idx_order_01", columnList="product_history_id")
        })
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy="org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id")
    private String id;

    @Column(name="count")
    private Integer count;

    @Column(name="price")
    private Integer price;

    /* Product Aplication Database Schema가 달라서 FK 불가 */
    @Column(name="product_history_id")
    private String productHistoryId;

    @CreationTimestamp
    @Column(name="created_time")
    private Date createdTime;

    @UpdateTimestamp
    @Column(name="updated_time")
    private Date updatedTime;
}
