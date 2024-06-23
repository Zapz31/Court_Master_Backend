package com.swp391.Court_Master.dto.request.Request;

import java.util.List;

public class CheckValidBooking {
    private String clubId;
    private List<PricePerSlotRequestDTO> perSlotRequestDTOs;
    public CheckValidBooking() {
    }
    public CheckValidBooking(String clubId, List<PricePerSlotRequestDTO> perSlotRequestDTOs) {
        this.clubId = clubId;
        this.perSlotRequestDTOs = perSlotRequestDTOs;
    }
    public String getClubId() {
        return clubId;
    }
    public void setClubId(String clubId) {
        this.clubId = clubId;
    }
    public List<PricePerSlotRequestDTO> getPerSlotRequestDTOs() {
        return perSlotRequestDTOs;
    }
    public void setPerSlotRequestDTOs(List<PricePerSlotRequestDTO> perSlotRequestDTOs) {
        this.perSlotRequestDTOs = perSlotRequestDTOs;
    }

    
}
