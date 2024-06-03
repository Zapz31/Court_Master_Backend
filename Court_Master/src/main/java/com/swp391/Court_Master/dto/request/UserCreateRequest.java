package com.swp391.Court_Master.dto.request;

import java.time.LocalDate;


public class UserCreateRequest {

    private String firstName;
    private String lastName;  
    private String email;
    private String password;
    private String phoneNumber;
    private int role;
    private LocalDate registerDate;
    private String avatarImageUrl;
    private String courtManagerId;

    
    
    public UserCreateRequest(String firstName, String lastName, String email, String password, String phoneNumber,
            int role, LocalDate registerDate, String avatarImageUrl, String courtManagerId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.registerDate = registerDate;
        this.avatarImageUrl = avatarImageUrl;
        this.courtManagerId = courtManagerId;
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
    
    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
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



    public LocalDate getRegisterDate() {
        return registerDate;
    }
    
}
