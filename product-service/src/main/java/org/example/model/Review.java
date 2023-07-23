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
public class Review {
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
    private Integer stars;
    private String comment;
    @ManyToOne
    @JoinColumn(
            name = "product_id",
            nullable = false
    )
    private Product product;
//    @ManyToOne
//    @JoinColumn(
//            name = "user_id",
//            nullable = false
//    )
//    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;

    public Review(String comment,
                  Integer stars) {
        this.comment = comment;
        this.stars = stars;
        this.createdAt = LocalDateTime.now();
        setLastUpdatedAt();
    }

    @PreUpdate
    private void setLastUpdatedAt() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
