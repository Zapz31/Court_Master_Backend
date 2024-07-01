package com.swp391.Court_Master.Entities;

public class PaymentUpdateBookingSchedule {
    private String bookingScheduleId;
    private Invoice invoice;
    private PaymentDetail paymentDetail;
    public PaymentUpdateBookingSchedule() {
    }
    public String getBookingScheduleId() {
        return bookingScheduleId;
    }
    public void setBookingScheduleId(String bookingScheduleId) {
        this.bookingScheduleId = bookingScheduleId;
    }
    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
    public PaymentUpdateBookingSchedule(String bookingScheduleId, Invoice invoice, PaymentDetail paymentDetail) {
        this.bookingScheduleId = bookingScheduleId;
        this.invoice = invoice;
        this.paymentDetail = paymentDetail;
    }

    
}
