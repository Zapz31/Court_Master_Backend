package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalDate;

public class UserInfoResponse {
    private String userId;
    private String emailOrPhoneNumber;
    private LocalDate birthDay;
    private LocalDate registerDate;
    private String email;
    private String phoneNumber;

    private String role;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String userId, String emailOrPhoneNumber, LocalDate birthDay, LocalDate registerDate,
            String email, String phoneNumber, String role) {
        this.userId = userId;
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.birthDay = birthDay;
        this.registerDate = registerDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailOrPhoneNumber() {
        return emailOrPhoneNumber;
    }

    public void setEmailOrPhoneNumber(String emailOrPhoneNumber) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
