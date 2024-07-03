package com.swp391.Court_Master.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingSlotFilterRequest {
    private String bookingScheduleId;
    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int isCheckIn;
    public BookingSlotFilterRequest() {
    }
    
    public BookingSlotFilterRequest(String bookingScheduleId, LocalDate bookingDate, LocalTime startTime,
            LocalTime endTime, int isCheckIn) {
        this.bookingScheduleId = bookingScheduleId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCheckIn = isCheckIn;
    }

    public String getBookingScheduleId() {
        return bookingScheduleId;
    }
    public void setBookingScheduleId(String bookingScheduleId) {
        this.bookingScheduleId = bookingScheduleId;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(int isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    
     
}
