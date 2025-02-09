package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalDate;

public class UserInfoResponse {
    private String userId;
    private LocalDate birthDay;
    private LocalDate registerDate;
    private String email;
    private String phoneNumber;
    private String imageUrlString;
    private String firstName;
    private String lastName;
    private String token;

    private String role;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String userId, LocalDate birthDay, LocalDate registerDate,
            String email, String phoneNumber, String role, String imageUrlString, String firstName, String lastName) {
        this.userId = userId;
        this.birthDay = birthDay;
        this.registerDate = registerDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.imageUrlString = imageUrlString;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    

    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getImageUrlString() {
        return imageUrlString;
    }

    public void setImageUrlString(String imageUrlString) {
        this.imageUrlString = imageUrlString;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    
}
