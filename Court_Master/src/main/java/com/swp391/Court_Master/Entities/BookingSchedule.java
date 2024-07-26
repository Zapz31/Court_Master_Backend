package com.swp391.Court_Master.Entities;

import java.time.LocalDate;
import java.util.List;

import com.swp391.Court_Master.dto.request.Respone.BookingSlotResponseDTO;

public class BookingSchedule {
    private String bookKingScheduleId;
    private String customerFullName;
    private String customerPhoneNumber;
    private String bookingScheduleStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String scheduleType;
    private String customerId;
    private int totalPrice;
    private int isView;
    private String totalPlayingTime;
    private int remainingAmount;
    private List<BookingSlotResponseDTO> bookingSlotResponseDTOs;
    
   
    
    
    public BookingSchedule(String bookKingScheduleId, String customerFullName, String customerPhoneNumber,
            String bookingScheduleStatus, LocalDate startDate, LocalDate endDate, String scheduleType,
            String customerId, int totalPrice, int isView, List<BookingSlotResponseDTO> bookingSlotResponseDTOs) {
        this.bookKingScheduleId = bookKingScheduleId;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.bookingScheduleStatus = bookingScheduleStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduleType = scheduleType;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.isView = isView;
        this.bookingSlotResponseDTOs = bookingSlotResponseDTOs;
    }
    
    
    public BookingSchedule() {
    }
    public String getBookKingScheduleId() {
        return bookKingScheduleId;
    }
    public void setBookKingScheduleId(String bookKingScheduleId) {
        this.bookKingScheduleId = bookKingScheduleId;
    }
    public String getCustomerFullName() {
        return customerFullName;
    }
    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
    public String getBookingScheduleStatus() {
        return bookingScheduleStatus;
    }
    public void setBookingScheduleStatus(String bookingScheduleStatus) {
        this.bookingScheduleStatus = bookingScheduleStatus;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public String getScheduleType() {
        return scheduleType;
    }
    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getIsView() {
        return isView;
    }
    public void setIsView(int isView) {
        this.isView = isView;
    }
    public List<BookingSlotResponseDTO> getBookingSlotResponseDTOs() {
        return bookingSlotResponseDTOs;
    }
    public void setBookingSlotResponseDTOs(List<BookingSlotResponseDTO> bookingSlotResponseDTOs) {
        this.bookingSlotResponseDTOs = bookingSlotResponseDTOs;
    }


    public String getTotalPlayingTime() {
        return totalPlayingTime;
    }


    public void setTotalPlayingTime(String totalPlayingTime) {
        this.totalPlayingTime = totalPlayingTime;
    }


    public int getRemainingAmount() {
        return remainingAmount;
    }


    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
    
}
