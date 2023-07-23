package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String zipCode;
    @Enumerated(EnumType.STRING)
    private List<UserRole> roles;
    private LocalDateTime createdAt;
    private LocalDateTime enabledAt;
    private LocalDateTime lastUpdatedAt;
    private Boolean isEnabled;
    @Column(columnDefinition = "bytea")
    private byte[] profilePicture;

    public User(String firstName,
                String lastName,
                String email,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isEnabled = false;
        this.roles = Collections.singletonList(UserRole.ROLE_USER);
        this.createdAt = LocalDateTime.now();
        setLastUpdatedAt();
    }

    @PreUpdate
    private void setLastUpdatedAt() {
        this.lastUpdatedAt = LocalDateTime.now();
    }

}
