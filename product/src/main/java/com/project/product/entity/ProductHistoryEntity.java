package com.project.product.entity;

import com.project.product.type.Action;
import com.project.product.type.Origin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access=AccessLevel.PUBLIC)
@ToString
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(
        name="tb_product_history",
        indexes={
                @Index(name="idx_product_history_01", columnList="name"),
                @Index(name="idx_product_history_02", columnList="created_time")
        })
public class ProductHistoryEntity extends ProductSuperEntity implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(name="action")
    private Action action;

    @CreationTimestamp
    @Column(name="created_time")
    private Date createdTime;

    @Column(name="product_id")
    private String productId;
}
