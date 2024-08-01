package com.swp391.Court_Master.Service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.swp391.Court_Master.Repository.AdminRepository;
import com.swp391.Court_Master.dto.request.Request.UpdateStaffRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchAccountByIdNamePhoneMail;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchClubByIdNameRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.UpdateAccountRequest;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.ClubDTO;
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

    public List<UserAccountDTO> searchAccountByIdNamePhoneMail(
            SearchAccountByIdNamePhoneMail searchAccountByIdNamePhoneMail) {
        List<UserAccountDTO> searchList = adminRepository.getAccountByIdNamePhoneMail(searchAccountByIdNamePhoneMail);
        return searchList;
    }

    public String banAccount(String userId) {
        boolean isBanAccount = adminRepository.isBanAccount(userId);
        if (isBanAccount) {
            return "success";
        } else {
            return "false";
        }
    }

    public String unbanAccount(String userId) {
        boolean isBanAccount = adminRepository.isUnbanAccount(userId);
        if (isBanAccount) {
            return "success";
        } else {
            return "false";
        }
    }

    public String updateAccount(UpdateAccountRequest UpdateAccountRequest) {
        boolean isUpdateAccount = adminRepository.updateAccountInfo(UpdateAccountRequest);
        if (isUpdateAccount) {
            return "success";
        } else {
            return "false";
        }
    }

    public List<ClubDTO> getAllClub() {
        List<ClubDTO> ClubList = adminRepository.getAllClub();
        return ClubList;
    }

    public List<ClubDTO> getAllActiveClub() {
        List<ClubDTO> ClubList = adminRepository.getAllActiveClub();
        return ClubList;
    }

    public List<ClubDTO> getAllInactiveClub() {
        List<ClubDTO> ClubList = adminRepository.getAllInactiveClub();
        return ClubList;
    }

    public List<ClubDTO> getAllStatusClub(String status) {
        List<ClubDTO> ClubList = adminRepository.getAllStatusClub(status);
        return ClubList;
    }

    public List<ClubDTO> searchClubByIdName(
            SearchClubByIdNameRequest SearchClubByIdNameRequest) {
        List<ClubDTO> ClubList = adminRepository.searchClubByIdName(SearchClubByIdNameRequest);
        return ClubList;
    }

    public String closeClub(String clubId) {
        boolean isCloseClub = adminRepository.isCloseClub(clubId);
        if (isCloseClub) {
            return "success";
        } else {
            return "false";
        }
    }

    public String openClub(String clubId) {
        boolean isOpenClub = adminRepository.isOpenClub(clubId);
        if (isOpenClub) {
            return "success";
        } else {
            return "false";
        }
    }
}
