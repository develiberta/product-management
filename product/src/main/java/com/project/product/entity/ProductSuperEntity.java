package com.project.product.entity;

import com.project.lib.type.Origin;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@Data
@MappedSuperclass
public class ProductSuperEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="UUID")
    @GenericGenerator(
            name="UUID",
            strategy="org.hibernate.id.UUIDGenerator"
    )
    @Column(name="id")
    private String id;

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
