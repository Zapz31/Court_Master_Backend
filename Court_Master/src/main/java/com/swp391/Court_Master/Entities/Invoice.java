package com.swp391.Court_Master.Entities;

public class Invoice {
    private String invoiceId;
    private String clubName;
    private String courtManagerPhone;
    private String bookingPhoneNumber;
    private String badmintonClubId;
    private String bookingScheduleId;
    public Invoice() {
    }
    public Invoice(String invoiceId, String clubName, String courtManagerPhone, String bookingPhoneNumber,
            String badmintonClubId, String bookingScheduleId) {
        this.invoiceId = invoiceId;
        this.clubName = clubName;
        this.courtManagerPhone = courtManagerPhone;
        this.bookingPhoneNumber = bookingPhoneNumber;
        this.badmintonClubId = badmintonClubId;
        this.bookingScheduleId = bookingScheduleId;
    }
    public String getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
    public String getBookingPhoneNumber() {
        return bookingPhoneNumber;
    }
    public void setBookingPhoneNumber(String bookingPhoneNumber) {
        this.bookingPhoneNumber = bookingPhoneNumber;
    }
    public String getBadmintonClubId() {
        return badmintonClubId;
    }
    public void setBadmintonClubId(String badmintonClubId) {
        this.badmintonClubId = badmintonClubId;
    }
    public String getBookingScheduleId() {
        return bookingScheduleId;
    }
    public void setBookingScheduleId(String bookingScheduleId) {
        this.bookingScheduleId = bookingScheduleId;
    }

    
}
