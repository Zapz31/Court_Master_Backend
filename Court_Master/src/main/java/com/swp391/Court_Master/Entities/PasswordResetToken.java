package com.swp391.Court_Master.Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;

@Entity
@NamedNativeQuery(
    name = "PasswordResetToken.getTokenByUserId",
    query = "select user_id as userId, token, expiration_time as expirationTime from password_reset_token where user_id = :userId",
    resultSetMapping = "ExsitToken"
)
@SqlResultSetMapping(
    name = "ExsitToken",
    classes = @ConstructorResult(
        targetClass = PasswordResetToken.class,
        columns = {
            @ColumnResult(name = "userId", type = String.class),
            @ColumnResult(name = "token", type = String.class),
            @ColumnResult(name = "expirationTime", type = LocalDateTime.class),         
        }
    )
)

public class PasswordResetToken {

    @Id
    private String userId;

    private String token;

    private LocalDateTime expirationTime;
   

    public PasswordResetToken(String userId, String token, LocalDateTime expirationTime) {
        this.userId = userId;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    
}
