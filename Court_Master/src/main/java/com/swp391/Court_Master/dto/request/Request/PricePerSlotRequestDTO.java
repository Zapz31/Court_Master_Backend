package com.swp391.Court_Master.dto.request.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public class PricePerSlotRequestDTO {
    //String courtId, LocalTime startBooking, LocalTime endBooking, LocalDate bookingDate, String bookingType
    private String courtId;
    private LocalTime startBooking;
    private LocalTime endBooking;
    private LocalDate bookingDate;
    private String bookingType;
    public PricePerSlotRequestDTO() {
    }
    public PricePerSlotRequestDTO(String courtId, LocalTime startBooking, LocalTime endBooking, LocalDate bookingDate,
            String bookingType) {
        this.courtId = courtId;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
        this.bookingDate = bookingDate;
        this.bookingType = bookingType;
    }
    
    public String getCourtId() {
        return courtId;
    }
    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }
    public LocalTime getStartBooking() {
        return startBooking;
    }
    public void setStartBooking(LocalTime startBooking) {
        this.startBooking = startBooking;
    }
    public LocalTime getEndBooking() {
        return endBooking;
    }
    public void setEndBooking(LocalTime endBooking) {
        this.endBooking = endBooking;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getBookingType() {
        return bookingType;
    }
    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

}
