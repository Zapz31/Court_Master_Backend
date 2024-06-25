package com.swp391.Court_Master.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookedDTO {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate bookingDate;
    private String courtId;
    private String courtName;
    private String bookingSlotId;
    private int isCheckIn;
    private int price;
    private String userFullName;
    public BookedDTO() {
    }
    
    public BookedDTO(LocalTime startTime, LocalTime endTime, LocalDate bookingDate, String courtId, String courtName,
            String bookingSlotId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingDate = bookingDate;
        this.courtId = courtId;
        this.courtName = courtName;
        this.bookingSlotId = bookingSlotId;
    }


    public BookedDTO(LocalTime startTime, LocalTime endTime, LocalDate bookingDate, String courtId, String courtName,
            String bookingSlotId, int isCheckIn, int price, String userFullName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingDate = bookingDate;
        this.courtId = courtId;
        this.courtName = courtName;
        this.bookingSlotId = bookingSlotId;
        this.isCheckIn = isCheckIn;
        this.price = price;
        this.userFullName = userFullName;
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
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getCourtId() {
        return courtId;
    }
    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }
    public String getCourtName() {
        return courtName;
    }
    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getBookingSlotId() {
        return bookingSlotId;
    }

    public void setBookingSlotId(String bookingSlotId) {
        this.bookingSlotId = bookingSlotId;
    }

    public int getIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(int isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    
}
