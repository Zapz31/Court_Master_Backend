package com.swp391.Court_Master.dto.request.Respone.StaffScreenView;

import java.time.LocalDate;
import java.time.LocalTime;

public class StaffViewBookingSlotDTO {
    private String bookingSlotId;
    private String customerPhoneNumber;
    private String customerFullName;
    private String badmintonCourtName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate bookingDate;
    private int isCheckIn;
    private int price;
    public StaffViewBookingSlotDTO() {
    }

    public StaffViewBookingSlotDTO(String bookingSlotId, String customerPhoneNumber, String customerFullName,
            String badmintonCourtName, LocalTime startTime, LocalTime endTime, LocalDate bookingDate, int isCheckIn,
            int price) {
        this.bookingSlotId = bookingSlotId;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerFullName = customerFullName;
        this.badmintonCourtName = badmintonCourtName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingDate = bookingDate;
        this.isCheckIn = isCheckIn;
        this.price = price;
    }
    
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
    public String getCustomerFullName() {
        return customerFullName;
    }
    public void setCustomerFullName(String customerFullName) {
        this.customerFullName = customerFullName;
    }
    public String getBadmintonCourtName() {
        return badmintonCourtName;
    }
    public void setBadmintonCourtName(String badmintonCourtName) {
        this.badmintonCourtName = badmintonCourtName;
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

    public String getBookingSlotId() {
        return bookingSlotId;
    }

    public void setBookingSlotId(String bookingSlotId) {
        this.bookingSlotId = bookingSlotId;
    }

    
}
