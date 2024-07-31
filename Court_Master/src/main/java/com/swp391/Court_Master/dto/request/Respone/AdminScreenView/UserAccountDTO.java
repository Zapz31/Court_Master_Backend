package com.swp391.Court_Master.dto.request.Respone.AdminScreenView;

import java.time.LocalDate;

import org.springframework.cglib.core.Local;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(UserAccountDTO.class.getName());

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

    // public boolean isEmailValid() {

    //     String emailToCheck = this.email;
    //     boolean isValid = emailToCheck != null && emailToCheck.contains("@") && emailToCheck.contains(".");
    //     return isValid;
    // }

    // public boolean isPhoneNumberValid() {

    //     String phoneNumberToCheck = this.phoneNumber;
    //     boolean isValid = phoneNumberToCheck != null && phoneNumberToCheck.matches("\\d{10}");
    //     return isValid;
    // }

    // public String getFullName() {

    //     String firstName = this.firstName;
    //     String lastName = this.lastName;
    //     String fullName = firstName + " " + lastName;
    //     return fullName;
    // }


    // public String getEmailDomain() {

    //     String emailToCheck = this.email;
    //     String domain = emailToCheck.substring(emailToCheck.indexOf("@") + 1);
    //     return domain;
    // }


    // public boolean isUserActive() {

    //     boolean isActive = "active".equalsIgnoreCase(this.userStatus);
    //     return isActive;
    // }


    // public boolean isUserAdmin() {

    //     boolean isAdmin = "admin".equalsIgnoreCase(this.role);
    //     return isAdmin;
    // }

}
