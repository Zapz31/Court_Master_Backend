package com.swp391.Court_Master.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.swp391.Court_Master.Entities.AuthenticatedUser;
import com.swp391.Court_Master.Entities.PasswordResetToken;
import com.swp391.Court_Master.Repository.UserValidatationDTORepository;
import com.swp391.Court_Master.Repository.PasswordResetTokenRepository;
import com.swp391.Court_Master.Repository.UserRepository;
import com.swp391.Court_Master.Utils.UserInputValidate;
import com.swp391.Court_Master.dto.request.UserCreateRequest;
import com.swp391.Court_Master.dto.request.UserValidationDTO;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

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
                authenUser.setRegisterDate(user.getRegisterDate());
                userRepository.save(authenUser);
                errorMap.put("duplicateError", "none");
            }
        }
        return errorMap;
    }

    public AuthenticatedUser getUserById(String id){
        List<AuthenticatedUser> userList = userRepository.findByUserId(id);
        AuthenticatedUser user = userList.get(0);
        return user;
    } 

    public AuthenticatedUser getUserByEmail(String email) throws IOException{
        AuthenticatedUser authenticatedUser = userRepository.findByEmailOrPhoneNumberPRT(email)
          .orElseThrow(() -> new UsernameNotFoundException("Your email is not registered"));
          authenticatedUser.setAvatarImageUrl(createBase64String(authenticatedUser.getAvatarImageUrl()));
          return authenticatedUser;
    }

    public void CreateResetPasswordTokenForUser( PasswordResetToken passwordResetToken){
        passwordResetTokenRepository.addPasswordResetToken(passwordResetToken.getUserId(), passwordResetToken.getToken(), passwordResetToken.getExpirationTime());
    }

    public String createToken(){
        String token = "";
        Random random = new Random(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 6; i++){
            int randomNumber = random.nextInt(10); //int randomNumber = random.nextInt((max - min) + 1) + min;
            stringBuilder.append(randomNumber);
        }
        token = stringBuilder.toString();
        return token;
    }

    public PasswordResetToken getToken(String userId){
        List<PasswordResetToken> list = passwordResetTokenRepository.getTokenByUserId(userId);
         PasswordResetToken token = list.get(0);
         return token;
    }

    public void updatePassword(String newPassword, String email){
        newPassword = passwordEncoder.encode(newPassword);
        userRepository.updatepassword(newPassword, email);

    }

    public void RemoveToken(String token){
        passwordResetTokenRepository.RemoveToken(token);
    }

    public void RemoveTokenByUserId(String userId){
        passwordResetTokenRepository.RemoveTokenByUserId(userId);
    }

    public String createBase64String(String imageURL) throws IOException{
        File userImage = new File("user-image");
        String userImageAbsolutePath = userImage.getAbsolutePath() + "/";
        File file = new File(userImageAbsolutePath + imageURL);
        Path path = Paths.get(file.getAbsolutePath());
        byte[] bytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(bytes);
        return base64Image;
    }

    /*
     * File clubImage = new File("club-image");
        String clubimageAbsolutePath = clubImage.getAbsolutePath() + "/";
        List<ClubHomePageResponse> list = clubHomePageService.getAllClubHomePage();
        for (ClubHomePageResponse clubHomePageResponse : list) {
            String imageFileName = clubHomePageResponse.getClubImageName();
            File file = new File(clubimageAbsolutePath + imageFileName);
            Path path = Paths.get(file.getAbsolutePath());
            byte[] bytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(bytes);
            clubHomePageResponse.setClubImageBase64(base64Image);
            
        }
    */




    // Create an error message array
    // public ArrayList<String> errorArr(AuthenticatedUser user){

    // }

}
