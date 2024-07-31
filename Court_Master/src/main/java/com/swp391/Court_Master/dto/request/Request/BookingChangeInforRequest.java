package com.swp391.Court_Master.dto.request.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingChangeInforRequest {
    private String scheduleId;
    private String oldBookingSlotId;
    private LocalTime startBooking;
    private LocalTime endBooking;
    private LocalDate bookingDate;
    private int price;
    private String courtId;
    private int isTemp;
    public BookingChangeInforRequest() {
    }
    public BookingChangeInforRequest(String scheduleId, String oldBookingSlotId, LocalTime startBooking,
            LocalTime endBooking, LocalDate bookingDate, int price, String courtId, int isTemp) {
        this.scheduleId = scheduleId;
        this.oldBookingSlotId = oldBookingSlotId;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
        this.bookingDate = bookingDate;
        this.price = price;
        this.courtId = courtId;
        this.isTemp = isTemp;
    }
    public String getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getOldBookingSlotId() {
        return oldBookingSlotId;
    }
    public void setOldBookingSlotId(String oldBookingSlotId) {
        this.oldBookingSlotId = oldBookingSlotId;
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
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getCourtId() {
        return courtId;
    }
    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }
    public int getIsTemp() {
        return isTemp;
    }
    public void setIsTemp(int isTemp) {
        this.isTemp = isTemp;
    }
    
}
