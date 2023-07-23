package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.model.ConfirmationToken;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByUser(User user);
    ConfirmationToken findByToken(String token);
    @Modifying
    @Transactional
    @Query("UPDATE ConfirmationToken ct SET ct.expiresAt = :expiresAt WHERE ct.user = :user")
    void updateConfirmationTokenExpiresAt(@Param("user") User user,
                                          @Param("expiresAt") LocalDateTime expiresAt);
    @Modifying
    @Transactional
    @Query("UPDATE ConfirmationToken ct SET ct.confirmedAt = :confirmedAt WHERE ct.token = :token")
    void updateConfirmationTokenConfirmedAt(@Param("token")String token,
                                            @Param("confirmedAt") LocalDateTime confirmedAt);

}
