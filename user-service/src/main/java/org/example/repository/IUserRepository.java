package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.dto.UpdateUserRequest;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isEnabled = true WHERE u.email = :email")
    void updateUserIsEnabled(@Param("email") String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :id")
    void updateUserPassword(@Param("id") Long id, @Param("password") String password);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET " +
            "u.phone = CASE WHEN :#{#dto.phone} IS NOT NULL THEN :#{#dto.phone} ELSE u.phone END, " +
            "u.address = CASE WHEN :#{#dto.address} IS NOT NULL THEN :#{#dto.address} ELSE u.address END, " +
            "u.city = CASE WHEN :#{#dto.city} IS NOT NULL THEN :#{#dto.city} ELSE u.city END, " +
            "u.country = CASE WHEN :#{#dto.country} IS NOT NULL THEN :#{#dto.country} ELSE u.country END, " +
            "u.zipCode = CASE WHEN :#{#dto.zipCode} IS NOT NULL THEN :#{#dto.zipCode} ELSE u.zipCode END " +
            "WHERE u.id = :id")
    void updateUser(Long id, UpdateUserRequest dto);

}
