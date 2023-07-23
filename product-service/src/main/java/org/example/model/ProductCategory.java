package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class ProductCategory {
    @Id
    @SequenceGenerator(
            name = "product_category_sequence",
            sequenceName = "product_category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_category_sequence"
    )
    private Long id;
    @NotBlank
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public ProductCategory(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        setLastUpdatedAt();
    }

    @PreUpdate
    private void setLastUpdatedAt() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
