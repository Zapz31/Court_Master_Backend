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
        List<UserAccountDTO> StaffList = adminRepository.getAllUserAccount();
        return StaffList;
    }

    public List<UserAccountDTO> getAllCustomerAccount() {
        List<UserAccountDTO> StaffList = adminRepository.getAllCustomerAccount();
        return StaffList;
    }

    public List<UserAccountDTO> getAllCourtManagerAccount() {
        List<UserAccountDTO> StaffList = adminRepository.getAllCourtManagerAccount();
        return StaffList;
    }

    public List<UserAccountDTO> getAllStaffAccount() {
        List<UserAccountDTO> StaffList = adminRepository.getAllStaffAccount();
        return StaffList;
    }

}
