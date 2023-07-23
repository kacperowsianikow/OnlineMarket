package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String size;
    @NotBlank
    private Long price;
    @NotBlank
    private Integer quantityAvailable;
    private Boolean isAvailable;
    @OneToMany
    @JoinColumn(
            nullable = false
    )
    private List<ProductCategory> productCategories;
    @OneToMany
    @JoinColumn
    private List<ProductImage> productImages;
    @ManyToOne
    @JoinColumn(
            name = "brand_id",
            nullable = false
    )
    private Brand brand;
    @OneToMany
    @JoinColumn
    private List<Review> reviews;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public Product(String name,
                   String description,
                   Long price,
                   Integer quantityAvailable,
                   LocalDateTime createdAt,
                   List<ProductCategory> productCategories,
                   Brand brand) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
        this.createdAt = createdAt;
        this.productCategories = productCategories;
        this.brand = brand;
        this.createdAt = LocalDateTime.now();
        updateLastUpdatedAtAndIsAvailable();
    }

    @PreUpdate
    @PostLoad
    private void updateLastUpdatedAtAndIsAvailable() {
        this.lastUpdatedAt = LocalDateTime.now();
        this.isAvailable = this.quantityAvailable > 0;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        updateLastUpdatedAtAndIsAvailable();
    }

}
