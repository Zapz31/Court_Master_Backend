package com.swp391.Court_Master.dto.request.Request;

import java.time.LocalDateTime;

public class PasswordResetTokenRequest {
    private String token;
    private LocalDateTime expirationTime;
    public PasswordResetTokenRequest(String token, LocalDateTime expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
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
