package com.swp391.Court_Master.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.swp391.Court_Master.Entities.AuthenticatedUser;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthenticatedUser, String>{
     
    List<AuthenticatedUser> findByUserId(String userId);

    @Query(name = "User.findUserByPhone", nativeQuery = true)
    Optional<AuthenticatedUser> findByEmailOrPhoneNumber(@Param("userInput") String emailOrPhoneNumber);
    
}
