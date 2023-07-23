package org.example.dto;

import org.example.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserAdminResponse(Long id,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   List<UserRole> userRoles,
                                   LocalDateTime createdAt,
                                   LocalDateTime enabledAt,
                                   LocalDateTime lastUpdatedAt,
                                   Boolean isAccountNonExpired) {

}
