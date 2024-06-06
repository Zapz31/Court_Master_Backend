package com.swp391.Court_Master.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.PasswordResetToken;

import jakarta.transaction.Transactional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String>{
    @Modifying
    @Transactional
    @Query(value = "insert into password_reset_token(user_id, token, expiration_time, is_expired)\r\n" + //
                "values(:userId, :token, :expirationTime, 0)", nativeQuery = true)
    public void addPasswordResetToken(@Param("userId") String userId, 
                                     @Param("token") String token, 
                                     @Param("expirationTime") LocalDateTime expirationTime);

    @Modifying
    @Transactional
    @Query(name = "PasswordResetToken.getTokenByUserId", nativeQuery = true)
    List<PasswordResetToken> getTokenByUserId(@Param("userId") String userId);
}
