package com.swp391.Court_Master.dto.request;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LoginRequest {
    private String emailOrPhoneNumber;
    private String password;
    private int roleId;
    private String roleName;

    private Collection<? extends GrantedAuthority> authorities;

    

    public String getRoleName(int roleId){
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

    public GrantedAuthority getGrantedAuthority(String roleName){
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        return authority;
    }
    //GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);

    public String getEmailOrPhoneNumber() {
        return emailOrPhoneNumber;
    }
    public void setEmailOrPhoneNumber(String emailOrPhoneNumber) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest() {
    }

    

    public LoginRequest(String emailOrPhoneNumber, String password, int roleId) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.password = password;
        this.roleId = roleId;
    }

    public LoginRequest(String emailOrPhoneNumber, String password) {
        this.emailOrPhoneNumber = emailOrPhoneNumber;
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    

    


     
    
}
