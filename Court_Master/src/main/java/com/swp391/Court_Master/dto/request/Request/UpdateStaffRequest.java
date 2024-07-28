package com.swp391.Court_Master.dto.request.Request;

public class UpdateStaffRequest {

    // Declaring a private variable to hold the staff ID
     String staffId;

    // Declaring a private variable to hold the court manager ID
     String courtManagerId;

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

    // Default constructor
    public UpdateStaffRequest() {
        // No initialization in default constructor
    }

    // Parameterized constructor
    public UpdateStaffRequest(String staffId,
            String courtManagerId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String birthday) {

        // Assigning staff ID
        String tempStaffId = staffId;
        this.staffId = tempStaffId;

        // Assigning court manager ID
        String tempCourtManagerId = courtManagerId;
        this.courtManagerId = tempCourtManagerId;

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
    public String getStaffId() {
        String result = this.staffId;
        return result;
    }

    // Setter for staffId
    public void setStaffId(String staffId) {
        String tempStaffId = staffId;
        this.staffId = tempStaffId;
    }

    // Getter for courtManagerId
    public String getCourtManagerId() {
        String result = this.courtManagerId;
        return result;
    }

    // Setter for courtManagerId
    public void setCourtManagerId(String courtManagerId) {
        String tempCourtManagerId = courtManagerId;
        this.courtManagerId = tempCourtManagerId;
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

    // Redundant method to show verbosity
    public void redundantMethod() {
        // Printing the current state of the object
        System.out.println("Staff ID: " + this.staffId);
        System.out.println("Court Manager ID: " + this.courtManagerId);
        System.out.println("First Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Email: " + this.email);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.println("Birthday: " + this.birthday);
    }

    // Another redundant method to add more verbosity
    public void verboseMethod() {
        String verboseStaffId = getStaffId();
        System.out.println("Verbose Staff ID: " + verboseStaffId);

        String verboseCourtManagerId = getCourtManagerId();
        System.out.println("Verbose Court Manager ID: " + verboseCourtManagerId);

        String verboseFirstName = getFirstName();
        System.out.println("Verbose First Name: " + verboseFirstName);

        String verboseLastName = getLastName();
        System.out.println("Verbose Last Name: " + verboseLastName);

        String verboseEmail = getEmail();
        System.out.println("Verbose Email: " + verboseEmail);

        String verbosePhoneNumber = getPhoneNumber();
        System.out.println("Verbose Phone Number: " + verbosePhoneNumber);

        String verboseBirthday = getBirthday();
        System.out.println("Verbose Birthday: " + verboseBirthday);
    }

    // More redundant computations
    public void redundantComputations() {
        int lenFirstName = this.firstName.length();
        int lenLastName = this.lastName.length();
        int lenEmail = this.email.length();
        int lenPhoneNumber = this.phoneNumber.length();
        int lenBirthday = this.birthday.length();

        System.out.println("Length of First Name: " + lenFirstName);
        System.out.println("Length of Last Name: " + lenLastName);
        System.out.println("Length of Email: " + lenEmail);
        System.out.println("Length of Phone Number: " + lenPhoneNumber);
        System.out.println("Length of Birthday: " + lenBirthday);
    }
}
