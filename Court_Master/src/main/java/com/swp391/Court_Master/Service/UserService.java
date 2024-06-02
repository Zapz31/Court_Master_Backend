package com.swp391.Court_Master.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.AuthenticatedUser;
import com.swp391.Court_Master.Repository.userRepository;
import com.swp391.Court_Master.dto.request.UserCreateRequest;

@Service
public class UserService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticatedUser createUser(UserCreateRequest userCreateRequest) {
        AuthenticatedUser newUser = new AuthenticatedUser();
        newUser.setFirstName(userCreateRequest.getFirstName());
        newUser.setLastName(userCreateRequest.getLastName());
        newUser.setRole(userCreateRequest.getRole());
        return userRepository.save(newUser);
    }

    public HashMap<String, String> registerUser(AuthenticatedUser user) {
        HashMap<String, String> errorMap = new HashMap<>();
        boolean isValid = true;
        if (user.getPassword().trim() == null || user.getPassword().trim().isEmpty()) {
            errorMap.put("passwordEmptyError", "Invalid password !");
            isValid = false;
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            errorMap.put("FirstNameEmptyError", "First name can not be empty !");
            isValid = false;
        }


        if (isValid) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            try {
                if (userRepository.save(user) != null) {
                    errorMap.put("registerSuccessNoti", "Register successfully !");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                if (e.toString().contains("unique_email")) {
                    errorMap.put("emailDuplicateError", "This email has already registered !");
                }
            }
        }

        return errorMap;
    }

    public AuthenticatedUser getUserById(String id){
        List<AuthenticatedUser> userList = userRepository.findByUserId(id);
        AuthenticatedUser user = userList.get(0);
        return user;
    } 

    

    // Create an error message array
    // public ArrayList<String> errorArr(AuthenticatedUser user){

    // }

}
