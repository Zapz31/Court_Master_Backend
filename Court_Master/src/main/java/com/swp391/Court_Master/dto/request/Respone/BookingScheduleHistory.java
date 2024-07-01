package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalDate;

public class BookingScheduleHistory {
    private String clubName;
    private String courtManagerPhone;
    private String bookingScheduleId;
    private String bookingScheduleStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private String scheduleType;
    private int totalPrice;
    private int totalPlayingTime;
    private String totalPlayingTimeString;
    private String bookingPhoneNumber;
    
    
    
    
    public BookingScheduleHistory(String clubName, String courtManagerPhone, String bookingScheduleId,
            String bookingScheduleStatus, LocalDate startDate, LocalDate endDate, String scheduleType, int totalPrice,
            int totalPlayingTime, String bookingPhoneNumber) {
        this.clubName = clubName;
        this.courtManagerPhone = courtManagerPhone;
        this.bookingScheduleId = bookingScheduleId;
        this.bookingScheduleStatus = bookingScheduleStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduleType = scheduleType;
        this.totalPrice = totalPrice;
        this.totalPlayingTime = totalPlayingTime;
        this.bookingPhoneNumber = bookingPhoneNumber;
    }
    public BookingScheduleHistory() {
    }
    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public String getCourtManagerPhone() {
        return courtManagerPhone;
    }
    public void setCourtManagerPhone(String courtManagerPhone) {
        this.courtManagerPhone = courtManagerPhone;
    }
    public String getBookingScheduleId() {
        return bookingScheduleId;
    }
    public void setBookingScheduleId(String bookingScheduleId) {
        this.bookingScheduleId = bookingScheduleId;
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
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getTotalPlayingTime() {
        return totalPlayingTime;
    }
    public void setTotalPlayingTime(int totalPlayingTime) {
        this.totalPlayingTime = totalPlayingTime;
    }

    public String getTotalPlayingTimeString() {
        return totalPlayingTimeString;
    }

    public void setTotalPlayingTimeString(String totalPlayingTimeString) {
        this.totalPlayingTimeString = totalPlayingTimeString;
    }

    public String getBookingPhoneNumber() {
        return bookingPhoneNumber;
    }

    public void setBookingPhoneNumber(String bookingPhoneNumber) {
        this.bookingPhoneNumber = bookingPhoneNumber;
    }
    
}
