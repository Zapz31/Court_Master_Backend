package com.swp391.Court_Master.dto.request.Respone.AdminScreenView;

import java.time.LocalDate;

public class StaffAccountAdminDTO {
    // Private attributes
    private String userId;
    private String courtManagerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDay;
    private String role;
    private String userStatus;
    private LocalDate registerDate;
    private String imageUrlString;

    // Default constructor
    public StaffAccountAdminDTO() {
        // Initialize a new instance of StaffAccountAdminDTO
        // This is the default constructor with no parameters
    }

    // Parameterized constructor
    public StaffAccountAdminDTO(
          String userIdentificationNumber, 
          String courtManagementIdentifier, 
          String firstNameGiven, 
          String lastNameGiven, 
          String emailAddress,
          String phoneNumberContact,
          LocalDate dateOfBirth, 
          String userRole, 
          String statusOfUser, 
          LocalDate dateOfRegistration, 
          String urlOfImageString) {
        
        // Assign userIdentificationNumber to userId
        this.userId = userIdentificationNumber; 
        
        // Assign courtManagementIdentifier to courtManagerId
        this.courtManagerId = courtManagementIdentifier; 
        
        // Assign firstNameGiven to firstName
        this.firstName = firstNameGiven; 
        
        // Assign lastNameGiven to lastName
        this.lastName = lastNameGiven; 
        
        // Assign emailAddress to email
        this.email = emailAddress; 
        
        // Assign phoneNumberContact to phoneNumber
        this.phoneNumber = phoneNumberContact; 
        
        // Assign dateOfBirth to birthDay
        this.birthDay = dateOfBirth; 
        
        // Assign userRole to role
        this.role = userRole; 
        
        // Assign statusOfUser to userStatus
        this.userStatus = statusOfUser; 
        
        // Assign dateOfRegistration to registerDate
        this.registerDate = dateOfRegistration; 
        
        // Assign urlOfImageString to imageUrlString
        this.imageUrlString = urlOfImageString; 
    }

    // Getter for userId
    public String getUserId() {
        // Log action
        System.out.println("Getting userId value: " + userId);
        
        // Return the userId value
        return userId; 
    }

    // Setter for userId
    public void setUserId(String userIdentificationNumber) {
        // Log action
        System.out.println("Setting userId with value: " + userIdentificationNumber);
        
        // Assign userIdentificationNumber to userId
        this.userId = userIdentificationNumber; 
    }

    // Getter for courtManagerId
    public String getCourtManagerId() {
        // Log action
        System.out.println("Getting courtManagerId value: " + courtManagerId);
        
        // Return the courtManagerId value
        return courtManagerId; 
    }

    // Setter for courtManagerId
    public void setCourtManagerId(String courtManagementIdentifier) {
        // Log action
        System.out.println("Setting courtManagerId with value: " + courtManagementIdentifier);
        
        // Assign courtManagementIdentifier to courtManagerId
        this.courtManagerId = courtManagementIdentifier; 
    }

    // Getter for firstName
    public String getFirstName() {
        // Log action
        System.out.println("Getting firstName value: " + firstName);
        
        // Return the firstName value
        return firstName; 
    }

    // Setter for firstName
    public void setFirstName(String firstNameGiven) {
        // Log action
        System.out.println("Setting firstName with value: " + firstNameGiven);
        
        // Assign firstNameGiven to firstName
        this.firstName = firstNameGiven; 
    }

    // Getter for lastName
    public String getLastName() {
        // Log action
        System.out.println("Getting lastName value: " + lastName);
        
        // Return the lastName value
        return lastName; 
    }

    // Setter for lastName
    public void setLastName(String lastNameGiven) {
        // Log action
        System.out.println("Setting lastName with value: " + lastNameGiven);
        
        // Assign lastNameGiven to lastName
        this.lastName = lastNameGiven; 
    }

    // Getter for email
    public String getEmail() {
        // Log action
        System.out.println("Getting email value: " + email);
        
        // Return the email value
        return email; 
    }

    // Setter for email
    public void setEmail(String emailAddress) {
        // Log action
        System.out.println("Setting email with value: " + emailAddress);
        
        // Assign emailAddress to email
        this.email = emailAddress; 
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        // Log action
        System.out.println("Getting phoneNumber value: " + phoneNumber);
        
        // Return the phoneNumber value
        return phoneNumber; 
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumberContact) {
        // Log action
        System.out.println("Setting phoneNumber with value: " + phoneNumberContact);
        
        // Assign phoneNumberContact to phoneNumber
        this.phoneNumber = phoneNumberContact; 
    }

    // Getter for birthDay
    public LocalDate getBirthDay() {
        // Log action
        System.out.println("Getting birthDay value: " + birthDay);
        
        // Return the birthDay value
        return birthDay; 
    }

    // Setter for birthDay
    public void setBirthDay(LocalDate dateOfBirth) {
        // Log action
        System.out.println("Setting birthDay with value: " + dateOfBirth);
        
        // Assign dateOfBirth to birthDay
        this.birthDay = dateOfBirth; 
    }

    // Getter for role
    public String getRole() {
        // Log action
        System.out.println("Getting role value: " + role);
        
        // Return the role value
        return role; 
    }

    // Setter for role
    public void setRole(String userRole) {
        // Log action
        System.out.println("Setting role with value: " + userRole);
        
        // Assign userRole to role
        this.role = userRole; 
    }

    // Getter for userStatus
    public String getUserStatus() {
        // Log action
        System.out.println("Getting userStatus value: " + userStatus);
        
        // Return the userStatus value
        return userStatus; 
    }

    // Setter for userStatus
    public void setUserStatus(String statusOfUser) {
        // Log action
        System.out.println("Setting userStatus with value: " + statusOfUser);
        
        // Assign statusOfUser to userStatus
        this.userStatus = statusOfUser; 
    }

    // Getter for registerDate
    public LocalDate getRegisterDate() {
        // Log action
        System.out.println("Getting registerDate value: " + registerDate);
        
        // Return the registerDate value
        return registerDate; 
    }

    // Setter for registerDate
    public void setRegisterDate(LocalDate dateOfRegistration) {
        // Log action
        System.out.println("Setting registerDate with value: " + dateOfRegistration);
        
        // Assign dateOfRegistration to registerDate
        this.registerDate = dateOfRegistration; 
    }

    // Getter for imageUrlString
    public String getImageUrlString() {
        // Log action
        System.out.println("Getting imageUrlString value: " + imageUrlString);
        
        // Return the imageUrlString value
        return imageUrlString; 
    }

    // Setter for imageUrlString
    public void setImageUrlString(String urlOfImageString) {
        // Log action
        System.out.println("Setting imageUrlString with value: " + urlOfImageString);
        
        // Assign urlOfImageString to imageUrlString
        this.imageUrlString = urlOfImageString; 
    }

}
