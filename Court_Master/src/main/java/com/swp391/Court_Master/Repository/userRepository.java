package com.swp391.Court_Master.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entities.AuthenticatedUser;
import java.util.List;




@Repository
public interface userRepository extends JpaRepository<AuthenticatedUser, String>{
     
    List<AuthenticatedUser> findByUserId(String userId);
}
