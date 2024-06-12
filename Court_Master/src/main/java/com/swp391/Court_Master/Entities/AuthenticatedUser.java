package com.swp391.Court_Master.Entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;



@Entity
@NamedNativeQuery(
    name = "User.findUserByPhone",
    query = "select email, phone_number as phoneNumber,user_id as userId, password, birth_day as birthDay, \r\n" + //
                "register_date as registerDate, role, avatar_image_url as avatarImageUrl, \r\n" + //
                "first_name as firstName, last_name as lastName from authenticated_user\r\n" + //
                "where email = :userInput or phone_number = :userInput",
    resultSetMapping = "ExsitUser"
)
@SqlResultSetMapping(
    name = "ExsitUser",
    classes = @ConstructorResult(
        targetClass = AuthenticatedUser.class,
        columns = {
            @ColumnResult(name = "email", type = String.class),
            @ColumnResult(name = "phoneNumber", type = String.class),
            @ColumnResult(name = "userId", type = String.class),
            @ColumnResult(name = "birthDay", type = LocalDate.class),
            @ColumnResult(name = "registerDate", type = LocalDate.class),
            @ColumnResult(name = "role", type = Integer.class),
            @ColumnResult(name = "password", type = String.class),       
            @ColumnResult(name = "avatarImageUrl", type = String.class),
            @ColumnResult(name = "firstName", type = String.class), 
            @ColumnResult(name = "lastName", type = String.class)
        }
    )
)
@Table(name = "authenticated_user")
public class AuthenticatedUser{
    @Id
    private String userId;
    
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    private String password;

    private String phoneNumber;
    private LocalDate birthDay;
    private int role;
    private int user_status;
    private LocalDate registerDate;
    private String avatarImageUrl;
    private String courtManagerId;

    

    public AuthenticatedUser() {
    }

    public AuthenticatedUser(String userId, String firstName, String lastName, String email, String password,
            String phoneNumber, LocalDate birthDay, int role, int user_status, LocalDate registerDate,
            String avatarImageUrl, String courtManagerId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
        this.user_status = user_status;
        this.registerDate = registerDate;
        this.avatarImageUrl = avatarImageUrl;
        this.courtManagerId = courtManagerId;
    }
    

    
    public AuthenticatedUser(String userId, String email, String phoneNumber, LocalDate birthDay, int role,
            LocalDate registerDate, String avatarImageUrl) {
        this.userId = userId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.role = role;
        this.registerDate = registerDate;
        this.avatarImageUrl = avatarImageUrl;
    }

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

    public String getRoleNameAuthenticate(int roleId){
        String roleName;
        switch (roleId) {
            case 1:
                roleName = "USER_CUSTOMER";
                break;
            case 2:
                roleName = "USER_COURT_MANAGER";
                break;
            case 3:
                roleName = "USER_COURT_STAFF";
                break;
            default:
                roleName = "SYSTEM_ADMIN";
        } 
        return roleName;
    }


    
}
