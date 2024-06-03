package com.swp391.Court_Master.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.swp391.Court_Master.dto.request.UserValidationDTO;

public interface UserValidatationDTORepository extends JpaRepository<UserValidationDTO, String>{
    @Query(name = "UserValidate.findByMailAndPhone", nativeQuery = true)
    List<UserValidationDTO> validateUserRegistration(
        @Param("email") String email, 
        @Param("phoneNumber") String phoneNumber
        ); 
}
