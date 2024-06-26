package com.swp391.Court_Master.dto.request.Request;

import com.swp391.Court_Master.Entities.BookingSchedule;
import com.swp391.Court_Master.Entities.PaymentDetail;

public class BookingPaymentRequestDTO {
    private BookingSchedule bookingSchedule;
    private PaymentDetail paymentDetail;
    public BookingPaymentRequestDTO() {
    }
    public BookingPaymentRequestDTO(BookingSchedule bookingSchedule, PaymentDetail paymentDetail) {
        this.bookingSchedule = bookingSchedule;
        this.paymentDetail = paymentDetail;
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

    
}
