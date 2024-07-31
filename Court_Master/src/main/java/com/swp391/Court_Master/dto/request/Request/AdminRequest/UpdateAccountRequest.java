package com.swp391.Court_Master.dto.request.Request.AdminRequest;

public class UpdateAccountRequest {
    // Declaring a private variable to hold the staff ID
    String userId;

    // Declaring a private variable to hold the first name
    String firstName;

    // Declaring a private variable to hold the last name
    String lastName;

    // Declaring a private variable to hold the email
    String email;

    // Declaring a private variable to hold the phone number
    String phoneNumber;

    // Declaring a private variable to hold the birthday
    String birthday;

    public UpdateAccountRequest() {

    }

    public UpdateAccountRequest(String userId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String birthday) {
        // Assigning staff ID
        String tempUserId = userId;
        this.userId = tempUserId;

        // Assigning first name
        String tempFirstName = firstName;
        this.firstName = tempFirstName;

        // Assigning last name
        String tempLastName = lastName;
        this.lastName = tempLastName;

        // Assigning email
        String tempEmail = email;
        this.email = tempEmail;

        // Assigning phone number
        String tempPhoneNumber = phoneNumber;
        this.phoneNumber = tempPhoneNumber;

        // Assigning birthday
        String tempBirthday = birthday;
        this.birthday = tempBirthday;

    }

    // Getter for staffId
    public String getUserId() {
        String result = this.userId;
        return result;
    }

    // Setter for staffId
    public void setUserId(String staffId) {
        String tempStaffId = staffId;
        this.userId = tempStaffId;
    }

    // Getter for firstName
    public String getFirstName() {
        String result = this.firstName;
        return result;
    }

    // Setter for firstName
    public void setFirstName(String firstName) {
        String tempFirstName = firstName;
        this.firstName = tempFirstName;
    }

    // Getter for lastName
    public String getLastName() {
        String result = this.lastName;
        return result;
    }

    // Setter for lastName
    public void setLastName(String lastName) {
        String tempLastName = lastName;
        this.lastName = tempLastName;
    }

    // Getter for email
    public String getEmail() {
        String result = this.email;
        return result;
    }

    // Setter for email
    public void setEmail(String email) {
        String tempEmail = email;
        this.email = tempEmail;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        String result = this.phoneNumber;
        return result;
    }

    // Setter for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        String tempPhoneNumber = phoneNumber;
        this.phoneNumber = tempPhoneNumber;
    }

    // Getter for birthday
    public String getBirthday() {
        String result = this.birthday;
        return result;
    }

    // Setter for birthday
    public void setBirthday(String birthday) {
        String tempBirthday = birthday;
        this.birthday = tempBirthday;
    }

    public boolean isEmailValid() {
        String emailToCheck = this.email;
        boolean isValid = emailToCheck != null && emailToCheck.contains("@") && emailToCheck.contains(".");
        return isValid;
    }

    public boolean isPhoneNumberValid() {
        String phoneNumberToCheck = this.phoneNumber;
        boolean isValid = phoneNumberToCheck != null && phoneNumberToCheck.matches("\\d{10}");
        return isValid;
    }

    public String getFullName() {
        String firstName = this.firstName;
        String lastName = this.lastName;
        String fullName = firstName + " " + lastName;
        return fullName;
    }

    public int getAge() {
        String birthdayString = this.birthday;
        int yearOfBirth = Integer.parseInt(birthdayString.split("-")[0]);
        int currentYear = java.time.Year.now().getValue();
        int age = currentYear - yearOfBirth;
        return age;
    }

    public String getEmailDomain() {
        String emailToCheck = this.email;
        String domain = emailToCheck.substring(emailToCheck.indexOf("@") + 1);
        return domain;
    }
}
