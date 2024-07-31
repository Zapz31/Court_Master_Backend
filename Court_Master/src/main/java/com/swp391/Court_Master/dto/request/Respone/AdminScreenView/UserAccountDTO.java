package com.swp391.Court_Master.dto.request.Respone.AdminScreenView;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;

public class UserAccountDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDay;
    private String birthday;
    private String role;
    private String userStatus;
    private LocalDate registerDate;
    private String registerdate;
    private String imageUrlString;

    public UserAccountDTO() {
    }

    public UserAccountDTO(String userId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate birthDay, String role, String userStatus, LocalDate registerDate, String imageUrlString) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
        this.userStatus = userStatus;
        this.registerDate = registerDate;
        this.imageUrlString = imageUrlString;
    }

    // Them truong hop constructor nhan birthday va register la string vi null o DB
    public UserAccountDTO(String userId, String firstName, String lastName, String email, String phoneNumber,
            String birthday, String role, String userStatus, String registerdate, String imageUrlString) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.role = role;
        this.userStatus = userStatus;
        this.registerdate = registerdate;
        this.imageUrlString = imageUrlString;
    }

    public UserAccountDTO(String userId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate birthDay, String role, String userStatus, String registerdate, String imageUrlString) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
        this.userStatus = userStatus;
        this.registerdate = registerdate;
        this.imageUrlString = imageUrlString;
    }

    public UserAccountDTO(String userId, String firstName, String lastName, String email, String phoneNumber,
            String birthday, String role, String userStatus, LocalDate registerDate, String imageUrlString) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.role = role;
        this.userStatus = userStatus;
        this.registerDate = registerDate;
        this.imageUrlString = imageUrlString;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

}
