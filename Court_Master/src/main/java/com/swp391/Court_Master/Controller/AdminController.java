package com.swp391.Court_Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.storage.Acl.User;
import com.swp391.Court_Master.Entities.Club;
import com.swp391.Court_Master.Entities.Court;
import com.swp391.Court_Master.Service.AdminService;
import com.swp391.Court_Master.dto.request.Request.SearchStaffByPhoneNameRequest;
import com.swp391.Court_Master.dto.request.Request.UpdateStaffRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchAccountByIdNamePhoneMail;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchClubByIdNameRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.UpdateAccountRequest;
import com.swp391.Court_Master.dto.request.Respone.MessageResponse;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.ClubDTO;
import com.swp391.Court_Master.dto.request.Respone.AdminScreenView.UserAccountDTO;
import com.swp391.Court_Master.dto.request.Respone.CourManagerScreenView.StaffAccountDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserAccountDTO>> getAllUserAccount() {
        List<UserAccountDTO> list = adminService.getAllUserAccount();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-customer")
    public ResponseEntity<List<UserAccountDTO>> getAllCustomerAccount() {
        List<UserAccountDTO> list = adminService.getAllCustomerAccount();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-manager")
    public ResponseEntity<List<UserAccountDTO>> getAllCourtManagerAccount() {
        List<UserAccountDTO> list = adminService.getAllCourtManagerAccount();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-staff")
    public ResponseEntity<List<UserAccountDTO>> getAllStaffAccount() {
        List<UserAccountDTO> list = adminService.getAllStaffAccount();
        return ResponseEntity.ok().body(list);
    }

    // chon tu dropdown list, role = 1? 2? 3?
    @GetMapping("/get-all-role")
    public ResponseEntity<List<UserAccountDTO>> getAllSpecificRoleAccount(@RequestParam("role") String role) {
        List<UserAccountDTO> list = adminService.getAllSpecificRoleAccount(role);
        return ResponseEntity.ok().body(list);
    }

    // search account by id,name,phone,mail
    @PostMapping("/search-account")
    public ResponseEntity<List<UserAccountDTO>> searchAccountByIdNamePhoneMail(
            @RequestBody SearchAccountByIdNamePhoneMail SearchAccountByIdNamePhoneMail) {
        List<UserAccountDTO> list = adminService.searchAccountByIdNamePhoneMail(SearchAccountByIdNamePhoneMail);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/unban-account")
    public String unbanAccount(@RequestParam("userId") String userId) {

        return adminService.unbanAccount(userId);
    }

    @GetMapping("/ban-account")
    public String banAccount(@RequestParam("userId") String userId) {

        return adminService.banAccount(userId);
    }

    @PostMapping("/update-account")
    public String updateAccount(
            @RequestBody UpdateAccountRequest updateAccountRequest) {
        String result = adminService.updateAccount(updateAccountRequest);
        return result;
    }

    @GetMapping("/get-all-club")
    public ResponseEntity<List<ClubDTO>> getAllClub() {
        List<ClubDTO> list = adminService.getAllClub();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-active-club")
    public ResponseEntity<List<ClubDTO>> getAllActiveClub() {
        List<ClubDTO> list = adminService.getAllActiveClub();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-inactive-club")
    public ResponseEntity<List<ClubDTO>> getAllInactiveClub() {
        List<ClubDTO> list = adminService.getAllInactiveClub();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-status-club")
    public ResponseEntity<List<ClubDTO>> getAllStatusClub(@RequestParam("status") String status) {
        List<ClubDTO> list = adminService.getAllStatusClub(status);
        return ResponseEntity.ok().body(list);
    }

    // search account by id,name,phone,mail
    @PostMapping("/search-club")
    public ResponseEntity<List<ClubDTO>> searchClubByIdName(
            @RequestBody SearchClubByIdNameRequest SearchClubByIdNameRequest) {
        List<ClubDTO> list = adminService.searchClubByIdName(SearchClubByIdNameRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/close-club")
    public String closeClub(@RequestParam("clubId") String clubId) {

        return adminService.closeClub(clubId);
    }

    @GetMapping("/open-club")
    public String openClub(@RequestParam("clubId") String clubId) {

        return adminService.openClub(clubId);
    }

    @PostMapping("/update-court")
    public ResponseEntity<MessageResponse> updateCourt(@RequestBody Court newCourt) {
        adminService.updateCourtInfo(newCourt);

        return ResponseEntity.ok().body(new MessageResponse("success"));
    }

    @PostMapping("/update-club")
    public ResponseEntity<MessageResponse> updateClub(@RequestBody Club newClub) {
        adminService.updateClubInfo(newClub);

        return ResponseEntity.ok().body(new MessageResponse("success"));
    }

    @GetMapping("/get-courts-of-club")
    public ResponseEntity<List<Court>> getAllCourts(@RequestParam("clubId") String clubId) {
        List<Court> list = adminService.getAllCourts(clubId);
        return ResponseEntity.ok().body(list);
    }

}
