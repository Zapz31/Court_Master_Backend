package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingSlotResponseDTO {
    private String courtId;
    private LocalTime startBooking;
    private LocalTime endBooking;
    private LocalDate bookingDate;
    private String bookingType;
    private String playTime;
    private Integer price;
    private int isTemp;
    public BookingSlotResponseDTO() {
    }
    public BookingSlotResponseDTO(String courtId, LocalTime startBooking, LocalTime endBooking, LocalDate bookingDate,
            String bookingType, String playTime, Integer price) {
        this.courtId = courtId;
        this.startBooking = startBooking;
        this.endBooking = endBooking;
        this.bookingDate = bookingDate;
        this.bookingType = bookingType;
        this.playTime = playTime;
        this.price = price;
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
    public String getPlayTime() {
        return playTime;
    }
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public int getIsTemp() {
        return isTemp;
    }
    public void setIsTemp(int isTemp) {
        this.isTemp = isTemp;
    }

    
}
