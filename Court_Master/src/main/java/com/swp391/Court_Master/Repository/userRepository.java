package com.swp391.Court_Master.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391.Court_Master.Entity.AuthenticatedUser;



@Repository
public interface userRepository extends JpaRepository<AuthenticatedUser, String>{
     
    
}
