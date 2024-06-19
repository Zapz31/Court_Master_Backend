package com.swp391.Court_Master.dto.request.Request;

import java.time.LocalTime;

public class BookingSlotRequest {
    private String courtId;
    private LocalTime startBooking;
    private LocalTime endBooking;
    public BookingSlotRequest() {
    }
    public BookingSlotRequest(String courtId, LocalTime startBooking, LocalTime endBooking) {
        this.courtId = courtId;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
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

}
