package com.swp391.Court_Master.Service;

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
}
