package com.swp391.Court_Master.dto.request;

import java.time.LocalDate;


public class UserCreateRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDay;
    private int role;
    private int user_status;
    private LocalDate registerDate;
    private String avatarImageUrl;
    private String courtManagerId;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LocalDate getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public int getUser_status() {
        return user_status;
    }
    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }
    public LocalDate getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }
    public String getAvatarImageUrl() {
        return avatarImageUrl;
    }
    public void setAvatarImageUrl(String avatarImageUrl) {
        this.avatarImageUrl = avatarImageUrl;
    }
    public String getCourtManagerId() {
        return courtManagerId;
    }
    public void setCourtManagerId(String courtManagerId) {
        this.courtManagerId = courtManagerId;
    }
    
}
