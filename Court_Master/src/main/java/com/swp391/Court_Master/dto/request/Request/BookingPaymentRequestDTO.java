package com.swp391.Court_Master.dto.request.Request;

import com.swp391.Court_Master.Entities.BookingSchedule;
import com.swp391.Court_Master.Entities.PaymentDetail;

public class BookingPaymentRequestDTO {
    private String courtManagerPhone;
    private String clubId;
    private String clubName;
    private BookingSchedule bookingSchedule;
    private PaymentDetail paymentDetail;
    private TempBookingSlot tempBookingSlot;
    private String scheduleAndSlotIdTemp; // Lay id de cap nhat trang thai cho booking slot va them cac du lieu thanh toan va hoa don cho booking schedule
    public BookingPaymentRequestDTO() {
    }
     
    public BookingPaymentRequestDTO(String courtManagerPhone, String clubId, String clubName,
            BookingSchedule bookingSchedule, PaymentDetail paymentDetail) {
        this.courtManagerPhone = courtManagerPhone;
        this.clubId = clubId;
        this.clubName = clubName;
        this.bookingSchedule = bookingSchedule;
        this.paymentDetail = paymentDetail;
    }


    public BookingPaymentRequestDTO(String courtManagerPhone, String clubId, String clubName,
            BookingSchedule bookingSchedule, PaymentDetail paymentDetail, TempBookingSlot tempBookingSlot) {
        this.courtManagerPhone = courtManagerPhone;
        this.clubId = clubId;
        this.clubName = clubName;
        this.bookingSchedule = bookingSchedule;
        this.paymentDetail = paymentDetail;
        this.tempBookingSlot = tempBookingSlot;
    }

    
    
    public BookingSchedule getBookingSchedule() {
        return bookingSchedule;
    }
    public void setBookingSchedule(BookingSchedule bookingSchedule) {
        this.bookingSchedule = bookingSchedule;
    }
    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }

    public String getCourtManagerPhone() {
        return courtManagerPhone;
    }

    public void setCourtManagerPhone(String courtManagerPhone) {
        this.courtManagerPhone = courtManagerPhone;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public TempBookingSlot getTempBookingSlot() {
        return tempBookingSlot;
    }

    public void setTempBookingSlot(TempBookingSlot tempBookingSlot) {
        this.tempBookingSlot = tempBookingSlot;
    }

    public String getScheduleAndSlotIdTemp() {
        return scheduleAndSlotIdTemp;
    }

    public void setScheduleAndSlotIdTemp(String scheduleAndSlotIdTemp) {
        this.scheduleAndSlotIdTemp = scheduleAndSlotIdTemp;
    }

    
}
