package com.swp391.Court_Master.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Repository.StaffRepository;
import com.swp391.Court_Master.Service.StaffService;
import com.swp391.Court_Master.dto.request.Respone.StaffScreenView.StaffViewBookingSlotDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;

    // Lay danh sach tat ca cac booking slot chua duoc check in de hien thi ra man
    // hinh staff
    @GetMapping("/get-all-uncheckIn-bslist")
    public ResponseEntity<List<StaffViewBookingSlotDTO>> getUncheckAllBSList(@RequestParam("clubId") String clubId) {
        List<StaffViewBookingSlotDTO> list = staffService.getAllUncheckInBsList(clubId);
        return ResponseEntity.ok().body(list);
    }

    // Lay danh sach tat ca cac booking slot da duoc check in de hien thi ra man
    // hinh staff
    @GetMapping("/get-all-checkdIn-bslist")
    public ResponseEntity<List<StaffViewBookingSlotDTO>> getAllcheckInBSList(@RequestParam("clubId") String clubId) {
        List<StaffViewBookingSlotDTO> list = staffService.getAllCheckInBsList(clubId);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/staffCheckIn")
    public String staffCheckin(@RequestParam("slotId") String slotId) {

        if (staffRepository.isUpdateCheckIn(slotId)) {
            return "success";
        }
        return "false";
    }

    @GetMapping("/get-clubId-by-userId")
    public ResponseEntity<HashMap<String, String>> getMethodName(@RequestParam("staffId") String staffId) {
        HashMap<String, String> response = new HashMap<>();
        String clubId = staffService.getClubIdByStaffId(staffId);
        response.put("clubId", clubId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search-booked-list")
    public ResponseEntity<List<StaffViewBookingSlotDTO>> searchBookedList(@RequestParam("clubId") String clubId,
            @RequestParam("phoneOrname") String phoneNumberOrCusName, @RequestParam("isCheckIn") String isCheckInString)
            throws UnsupportedEncodingException {
        String phoneNumberOrCusNameDecode = URLDecoder.decode(phoneNumberOrCusName, "UTF-8");
        int isCheckIn = Integer.parseInt(isCheckInString);
        List<StaffViewBookingSlotDTO> results = staffService.searchByPhonenumberOrNameUncheckIn(clubId,
                phoneNumberOrCusNameDecode, isCheckIn);
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/staffUnCheckIn")
    public String staffUnCheckin(@RequestParam("slotId") String slotId) {

        return staffService.removeCheckIn(slotId);
    }

}