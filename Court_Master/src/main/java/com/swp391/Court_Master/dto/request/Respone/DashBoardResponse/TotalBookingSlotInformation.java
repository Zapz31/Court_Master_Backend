package com.swp391.Court_Master.dto.request.Respone.DashBoardResponse;

public class TotalBookingSlotInformation {
    private int totalBookingSlot;
    private int percentVariation;
    
    public TotalBookingSlotInformation(int totalBookingSlot, int percentVariation) {
        this.totalBookingSlot = totalBookingSlot;
        this.percentVariation = percentVariation;
    }
    public TotalBookingSlotInformation() {
    }
    public int getTotalBookingSlot() {
        return totalBookingSlot;
    }
    public void setTotalBookingSlot(int totalBookingSlot) {
        this.totalBookingSlot = totalBookingSlot;
    }
    public int getPercentVariation() {
        return percentVariation;
    }
    public void setPercentVariation(int percentVariation) {
        this.percentVariation = percentVariation;
    }

    
}
