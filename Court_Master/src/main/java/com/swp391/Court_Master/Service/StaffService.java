package com.swp391.Court_Master.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Repository.StaffRepository;
import com.swp391.Court_Master.dto.request.Respone.StaffScreenView.StaffViewBookingSlotDTO;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<StaffViewBookingSlotDTO> getAllUncheckInBsList(String clubId){
        List<StaffViewBookingSlotDTO> unCheckInBSList = staffRepository.getUncheckInBS(clubId);
        return unCheckInBSList;
    }

    public List<StaffViewBookingSlotDTO> getAllCheckInBsList(String clubId){
        List<StaffViewBookingSlotDTO> checkInBSList = staffRepository.getCheckedInBS(clubId);
        return checkInBSList;
    }

    // get clubId by StaffId
    public String getClubIdByStaffId(String staffId){
        return staffRepository.getClubIdByStaffId(staffId);
    }

    // Tim kiem booking slot o court manager o ca 2 man hinh check in va khong check in
    public List<StaffViewBookingSlotDTO> searchByPhonenumberOrNameUncheckIn(String clubId, String phoneNumberOrCusName, int isCheckIn){

        List<StaffViewBookingSlotDTO> checkOrUncheckList = new ArrayList<>();
        if(isCheckIn == 0){
            checkOrUncheckList = staffRepository.getUncheckInBS(clubId);
        } else {
            checkOrUncheckList = staffRepository.getCheckedInBS(clubId);
        }
        List<StaffViewBookingSlotDTO> results = new ArrayList<>();
        if(!phoneNumberOrCusName.equals("")){
            for(StaffViewBookingSlotDTO bs: checkOrUncheckList){
                if(bs.getCustomerPhoneNumber().contains(phoneNumberOrCusName) || bs.getCustomerFullName().contains(phoneNumberOrCusName)){
                    results.add(bs);
                } 
            }
        } else {
            results = checkOrUncheckList;
        }
        return results;
    }

    // Huy check in 
    public String removeCheckIn(String bookingSlotId){
        boolean isRemoveCheckIn = staffRepository.isRemoveUpdateCheckIn(bookingSlotId);
        if(isRemoveCheckIn){
            return "success";
        } else {
            return "false";
        }
    }
}
