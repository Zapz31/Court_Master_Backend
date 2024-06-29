package com.swp391.Court_Master.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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
import org.springframework.web.multipart.MultipartFile;

import com.swp391.Court_Master.Entities.AuthenticatedUser;
import com.swp391.Court_Master.Entities.PasswordResetToken;
import com.swp391.Court_Master.Service.EmailService;
import com.swp391.Court_Master.Service.UserDetailsImbl;
import com.swp391.Court_Master.Service.UserService;
import com.swp391.Court_Master.dto.request.ChangePasswordDTO;
import com.swp391.Court_Master.dto.request.LoginRequest;
import com.swp391.Court_Master.dto.request.UserCreateRequest;
import com.swp391.Court_Master.dto.request.Request.PasswordResetTokenRequest;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.UserInfoResponse;
import com.swp391.Court_Master.security.jwt.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


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

    @Autowired
    EmailService emailService;

    private final String ResetPasswordEmailSubject = "Reset password";
    

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> registerUser(@RequestBody UserCreateRequest user) {

        HashMap<String, String> registerNotic = userService.registerUser(user);

        return new ResponseEntity<>(registerNotic, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws IOException {
    GrantedAuthority authority = loginRequest.getGrantedAuthority(loginRequest.getRoleName(loginRequest.getRoleId()));
    List<GrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(authority);
    loginRequest.setAuthorities(authorityList);
    AuthenticatedUser user = userService.getUserByEmail(loginRequest.getEmailOrPhoneNumber());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrPhoneNumber(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImbl userDetails = (UserDetailsImbl) authentication.getPrincipal();

    String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
    UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUserId(), userDetails.getBirthDay(), userDetails.getRegisterDate(), userDetails.getEmail(), userDetails.getPhoneNumber(), user.getRoleNameAuthenticate(user.getRole()), user.getAvatarImageUrl(), user.getFirstName(), user.getLastName());
    userInfoResponse.setToken(jwt);
    return ResponseEntity.ok()
        .header("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With")
        .header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS")
        .body(userInfoResponse);
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

    // String body = "<html><body>"
    //     + "<h2>Example Email with a Link</h2>"
    //     + "<p>Click <a href='https://www.example.com'>here</a> to visit the example website.</p>"
    //     + "</body></html>";

    @PostMapping("/forgotpassword/{email}")
    public ResponseEntity<MessageResponse> postMethodName(@PathVariable("email") String email) throws IOException {
        //TODO: process POST request
        AuthenticatedUser authenticatedUser = userService.getUserByEmail(email);
        String token = userService.createToken();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(20);
        
        userService.RemoveTokenByUserId(authenticatedUser.getUserId());

        PasswordResetToken passwordResetToken = new PasswordResetToken(authenticatedUser.getUserId(), token, expirationTime);

        userService.CreateResetPasswordTokenForUser(passwordResetToken);
        emailService.sendEmail(email, ResetPasswordEmailSubject, "This is your otp to reset your password: " + token);
        MessageResponse resetPasswordMess = new MessageResponse("success");
        return new ResponseEntity<>(resetPasswordMess, HttpStatus.CREATED);
    }

    // @PostMapping("/sendEmail")
    // public ResponseEntity<String> sendEmail() {
        
    //     emailService.sendEmail("nhancd909@gmail.com", "Test send Email", "kẻ sống trong nắng hạ sao hiểu được giá lạnh ngày đông");
    //     return ResponseEntity.ok("Success !!!");
    // }

    @PostMapping("/forgotpassword/checktoken/{email}")
    public ResponseEntity<MessageResponse> validateToken(@PathVariable("email") String email, @RequestBody PasswordResetTokenRequest passwordResetTokenInput) throws IOException {
        String message = "Valid";
        AuthenticatedUser user = userService.getUserByEmail(email);
        PasswordResetToken passwordResetToken = userService.getToken(user.getUserId());
        if(!passwordResetToken.getToken().equals(passwordResetTokenInput.getToken())){
            return new ResponseEntity<>(new MessageResponse("Invalid Token"), HttpStatus.UNAUTHORIZED);
        } else if(!passwordResetTokenInput.getExpirationTime().isBefore(passwordResetToken.getExpirationTime())){
            userService.RemoveToken(passwordResetToken.getToken());
            return new ResponseEntity<>(new MessageResponse("Your token is expired"), HttpStatus.UNAUTHORIZED);
        }
        userService.RemoveToken(passwordResetToken.getToken());
        return ResponseEntity.ok(new MessageResponse(message));

    }

    @PutMapping("/updatepassword")
    public ResponseEntity<MessageResponse> putMethodName(@RequestBody ChangePasswordDTO changePasswordDTO) {
        //TODO: process PUT request
        userService.updatePassword(changePasswordDTO.getPassword(), changePasswordDTO.getEmail());
        return new ResponseEntity<>(new MessageResponse("Update successfully"), HttpStatus.ACCEPTED);
    }
  
}
