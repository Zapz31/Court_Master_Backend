package com.swp391.Court_Master.dto.request.Request;

public class UpdateStaffRequest {
    String staffId;
    String courtManagerId;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String birthday;

    public UpdateStaffRequest() {

    }

    public UpdateStaffRequest(String staffId,
            String courtManagerId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String birthday) {
        this.staffId = staffId;
        this.courtManagerId = courtManagerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    // Getter and Setter for staffId
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    // Getter and Setter for courtManagerId
    public String getCourtManagerId() {
        return courtManagerId;
    }

    public void setCourtManagerId(String courtManagerId) {
        this.courtManagerId = courtManagerId;
    }

    // Getter and Setter for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for birthday
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
