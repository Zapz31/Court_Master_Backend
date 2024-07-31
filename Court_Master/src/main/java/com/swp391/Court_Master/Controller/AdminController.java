package com.swp391.Court_Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.storage.Acl.User;
import com.swp391.Court_Master.Service.AdminService;
import com.swp391.Court_Master.dto.request.Request.SearchStaffByPhoneNameRequest;
import com.swp391.Court_Master.dto.request.Request.AdminRequest.SearchAccountByIdNamePhoneMail;
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

}
