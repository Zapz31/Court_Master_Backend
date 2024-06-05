package com.swp391.Court_Master.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Service.UserDetailsImbl;
import com.swp391.Court_Master.Service.UserService;
import com.swp391.Court_Master.dto.request.LoginRequest;
import com.swp391.Court_Master.dto.request.UserCreateRequest;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.UserInfoResponse;
import com.swp391.Court_Master.security.jwt.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpHeaders;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticatedUserController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> registerUser(@RequestBody UserCreateRequest user) {

        HashMap<String, String> registerNotic = userService.registerUser(user);

        return new ResponseEntity<>(registerNotic, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    GrantedAuthority authority = loginRequest.getGrantedAuthority(loginRequest.getRoleName(loginRequest.getRoleId()));
    List<GrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(authority);
    loginRequest.setAuthorities(authorityList);
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrPhoneNumber(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImbl userDetails = (UserDetailsImbl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

 

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getUserId(), 
                                    userDetails.getEmailOrPhoneNumber(), 
                                    userDetails.getBirthDay(), 
                                    userDetails.getRegisterDate(), 
                                    userDetails.getEmail(), 
                                    userDetails.getPhoneNumber(), 
                                    loginRequest.getRoleName(loginRequest.getRoleId())));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }

    // @GetMapping("/userById")
    // public ResponseEntity<AuthenticatedUser> getUserById( @RequestBody @Valid
    // UserCreateRequest userGet) {
    // AuthenticatedUser user = userService.getUserById(userGet.getUserId());
    // return new ResponseEntity<>(user, HttpStatus.CREATED);
    // }
}
