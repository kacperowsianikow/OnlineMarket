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
public class Brand {
    @Id
    @SequenceGenerator(
            name = "brand_sequence",
            sequenceName = "brand_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "brand_sequence"
    )
    private Long id;
    @NotBlank
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    @OneToMany
    @JoinColumn(
            name = "product_id"
    )
    private List<Product> products;

    public Brand(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        setLastUpdatedAt();
    }

    @PreUpdate
    private void setLastUpdatedAt() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
