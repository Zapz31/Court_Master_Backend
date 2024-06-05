package com.swp391.Court_Master.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
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
