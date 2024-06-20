package com.swp391.Court_Master.dto.request.Respone;

import java.util.List;

public class UnpaidBookingInfo {
    private Integer totalPrice;
    private String totalHour;
    private List<BookingSlotResponseDTO> unpaidBookingList;
    public UnpaidBookingInfo() {
    }
    public UnpaidBookingInfo(Integer totalPrice, String totalHour, List<BookingSlotResponseDTO> unpaidBookingList) {
        this.totalPrice = totalPrice;
        this.totalHour = totalHour;
        this.unpaidBookingList = unpaidBookingList;
    }
    public Integer getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getTotalHour() {
        return totalHour;
    }
    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }
    public List<BookingSlotResponseDTO> getUnpaidBookingList() {
        return unpaidBookingList;
    }
    public void setUnpaidBookingList(List<BookingSlotResponseDTO> unpaidBookingList) {
        this.unpaidBookingList = unpaidBookingList;
    }
    
}
