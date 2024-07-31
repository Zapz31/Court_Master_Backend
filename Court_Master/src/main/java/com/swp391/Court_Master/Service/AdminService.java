package com.swp391.Court_Master.Service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.swp391.Court_Master.Repository.AdminRepository;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public List<UserAccountDTO> getAllUserAccount() {
        List<UserAccountDTO> UserList = adminRepository.getAllUserAccount();
        return UserList;
    }

    public List<UserAccountDTO> getAllCustomerAccount() {
        List<UserAccountDTO> CusList = adminRepository.getAllCustomerAccount();
        return CusList;
    }

    public List<UserAccountDTO> getAllCourtManagerAccount() {
        List<UserAccountDTO> CourtManagerList = adminRepository.getAllCourtManagerAccount();
        return CourtManagerList;
    }

    public List<UserAccountDTO> getAllStaffAccount() {
        List<UserAccountDTO> StaffList = adminRepository.getAllStaffAccount();
        return StaffList;
    }

    public List<UserAccountDTO> getAllSpecificRoleAccount(String role) {
        List<UserAccountDTO> RoleList = adminRepository.getAllSpecificRoleAccount(role);
        return RoleList;
    }

}
