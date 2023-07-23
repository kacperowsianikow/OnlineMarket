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
public class ProductImage {
    @Id
    @SequenceGenerator(
            name = "product_image_sequence",
            sequenceName = "product_image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_image_sequence"
    )
    private Long id;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private Boolean isPrimary;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public ProductImage(String imageUrl,
                        Boolean isPrimary,
                        Product product) {
        this.imageUrl = imageUrl;
        this.isPrimary = isPrimary;
        this.product = product;
        this.createdAt = LocalDateTime.now();
        setLastUpdatedAt();
    }

    @PreUpdate
    private void setLastUpdatedAt() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
