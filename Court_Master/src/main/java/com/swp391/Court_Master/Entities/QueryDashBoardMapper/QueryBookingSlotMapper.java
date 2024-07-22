package com.swp391.Court_Master.Entities.QueryDashBoardMapper;

import java.time.LocalDate;

public class QueryBookingSlotMapper {
    LocalDate bookingDate;
    int numberOfBooking;
    public QueryBookingSlotMapper() {
    }
    public QueryBookingSlotMapper(LocalDate bookingDate, int numberOfBooking) {
        this.bookingDate = bookingDate;
        this.numberOfBooking = numberOfBooking;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public int getNumberOfBooking() {
        return numberOfBooking;
    }
    public void setNumberOfBooking(int numberOfBooking) {
        this.numberOfBooking = numberOfBooking;
    }
    
}
