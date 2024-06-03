package com.swp391.Court_Master.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.swp391.Court_Master.Service.UserService;
import com.swp391.Court_Master.dto.request.UserCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class AuthenticatedUserController {
    @Autowired
    private UserService userService;
  
    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> registerUser( @RequestBody UserCreateRequest user) {

        HashMap<String, String> registerNotic = userService.registerUser(user);
        
        return new ResponseEntity<>(registerNotic, HttpStatus.CREATED);
    }

    // @GetMapping("/userById")
    // public ResponseEntity<AuthenticatedUser> getUserById( @RequestBody @Valid UserCreateRequest userGet) {
    //     AuthenticatedUser user = userService.getUserById(userGet.getUserId());      
    //     return new ResponseEntity<>(user, HttpStatus.CREATED);
    // }
}
