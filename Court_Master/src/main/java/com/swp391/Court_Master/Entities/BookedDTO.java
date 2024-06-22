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

    
}
