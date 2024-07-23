package com.swp391.Court_Master.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.swp391.Court_Master.Entities.AuthenticatedUser;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthenticatedUser, String>{
     
    List<AuthenticatedUser> findByUserId(String userId);

    @Query(name = "User.findUserByPhone", nativeQuery = true)
    AuthenticatedUser findByEmailOrPhoneNumber(@Param("userInput") String emailOrPhoneNumber);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE authenticated_user\r\n" + //
                "SET password = :newPassword\r\n" + //
                "where email = :email", nativeQuery = true)
    public void updatepassword(@Param("newPassword") String newPassword, @Param("email") String email);
    

    @Query(name = "User.findUserByPhone", nativeQuery = true)
    Optional<AuthenticatedUser> findByEmailOrPhoneNumberPRT(@Param("userInput") String emailOrPhoneNumber);

    

    
}
