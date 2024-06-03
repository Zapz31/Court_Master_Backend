package com.swp391.Court_Master.Service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.AuthenticatedUser;
import com.swp391.Court_Master.Repository.UserValidatationDTORepository;
import com.swp391.Court_Master.Repository.userRepository;
import com.swp391.Court_Master.Utils.UserInputValidate;
import com.swp391.Court_Master.dto.request.UserCreateRequest;
import com.swp391.Court_Master.dto.request.UserValidationDTO;

@Service
public class UserService {
    @Autowired
    private userRepository userRepository;


    UserInputValidate userInputValidate;

    @Autowired
    UserValidatationDTORepository userValidatationDTORepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticatedUser createUser(UserCreateRequest userCreateRequest) {
        AuthenticatedUser newUser = new AuthenticatedUser();
        newUser.setFirstName(userCreateRequest.getFirstName());
        newUser.setLastName(userCreateRequest.getLastName());
        newUser.setRole(userCreateRequest.getRole());
        return userRepository.save(newUser);
    }

    public HashMap<String, String> registerUser(UserCreateRequest user) {
        HashMap<String, String> errorMap = new HashMap<>();
        List<UserValidationDTO> list;
        boolean isValid = true;
        userInputValidate = new UserInputValidate(); 
        String userFirstName = userInputValidate.getString(user.getFirstName(), 2, 300, "^(?!\\d)[\\p{L}\\s]+$", errorMap, 
        "First name", "firstNameError", "First name must start with a letter and have no special characters");
        if(userFirstName.equals("error")){
            isValid = false;
        }

        String userLastName = userInputValidate.getString(user.getLastName(), 2, 300, "^(?!\\d)[\\p{L}\\s]+$", errorMap, 
        "Last name", "lastNameError", "Last name must start with a letter and have no special characters");
        if(userLastName.equals("error")){
            isValid = false;
        } 

        String userPhoneNumber = userInputValidate.getString(user.getPhoneNumber(), 10, 10, "^0\\d{9}$", errorMap, 
        "Phone number", "phoneNumberError", "Please enter your phone number in the correct format");
        if(userPhoneNumber.equals("error")){
            isValid = false;
        } 
        // ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}
        String userEmail = userInputValidate.getString(user.getEmail(), 1, 100, "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", errorMap, 
        "Email", "emailError", "Please enter the following email format correctly: example@gmail.com");
        if(userEmail.equals("error")){
            isValid = false;
        }

        String userPassword = userInputValidate.getString(user.getPassword(), 1, 100, "^.*$", errorMap, 
        "Password", "passwordError", "");
        if(userPassword.equals("error")){
            isValid = false;
        }

        int userRole = user.getRole();
        if(isValid){
            list = userValidatationDTORepository.validateUserRegistration(userEmail, userPhoneNumber);
            if(list.size() != 0){
                errorMap.put("duplicateError", "Email or phone number has already exsit");
            } else {
                userPassword = passwordEncoder.encode(userPassword);
                AuthenticatedUser authenUser = new AuthenticatedUser();
                authenUser.setUserId("3");
                authenUser.setRole(userRole);
                authenUser.setFirstName(userFirstName);
                authenUser.setLastName(userLastName);
                authenUser.setPhoneNumber(userPhoneNumber);
                authenUser.setEmail(userEmail);
                authenUser.setPassword(userPassword);
                userRepository.save(authenUser);
                errorMap.put("registerMess", "success");
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
