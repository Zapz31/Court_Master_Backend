package com.swp391.Court_Master.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391.Court_Master.Entities.AuthenticatedUser;
import java.util.List;

@Service
public class UserDetailsImbl implements UserDetails{

    private String userId;
    private String emailOrPhoneNumber;
    private LocalDate birthDay;
    private LocalDate registerDate;
    private String email;
    private String phoneNumber;



    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    

    

    public UserDetailsImbl() {
    }



    public UserDetailsImbl(String userId,String emailorPhoneNumber, LocalDate birthDay, LocalDate registerDate,
            String email, String phoneNumber, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.birthDay = birthDay;
        this.registerDate = registerDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
        this.password = password;
        this.emailOrPhoneNumber = emailorPhoneNumber;
    }



    public static UserDetailsImbl build(AuthenticatedUser user, String emailOrPhoneNumber) {
    // List<GrantedAuthority> authorities = user.getRoles().stream()
    //     .map(role -> new SimpleGrantedAuthority(role.getName().name()))
    //     .collect(Collectors.toList());
        String roleName = user.getRoleNameAuthenticate(user.getRole());
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
    return new UserDetailsImbl(
        user.getUserId(),
        emailOrPhoneNumber, 
        user.getBirthDay(), 
        user.getRegisterDate(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getPassword(), 
        authorities);
  }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailOrPhoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmailOrPhoneNumber() {
        return emailOrPhoneNumber;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }



    public String getEmail() {
        return email;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    

}
