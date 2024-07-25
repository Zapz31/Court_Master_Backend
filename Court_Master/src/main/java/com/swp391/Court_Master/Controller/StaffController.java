package com.swp391.Court_Master.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp391.Court_Master.Service.StaffService;
import com.swp391.Court_Master.dto.request.Respone.StaffScreenView.StaffViewBookingSlotDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("/get-all-uncheckIn-bslist")
    public ResponseEntity<List<StaffViewBookingSlotDTO>> getUncheckAllBSList(@RequestParam("clubId") String clubId) {
        List<StaffViewBookingSlotDTO> list = staffService.getAllUncheckInBsList(clubId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/get-all-uncheckIn-bslist")
    public ResponseEntity<List<StaffViewBookingSlotDTO>> getAllcheckInBSList(@RequestParam("clubId") String clubId) {
        List<StaffViewBookingSlotDTO> list = staffService.getAllUncheckInBsList(clubId);
        return ResponseEntity.ok().body(list)
    }    
    
}
