package com.swp391.Court_Master.Entities;

import java.time.LocalDateTime;

public class PaymentDetail {
    private String paymentId; //uuid
    private int amount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String userId;
    private String invoiceId;
    private String amountHourString;
    public PaymentDetail() {
    }
    public PaymentDetail(String paymentId, int amount, String paymentMethod, LocalDateTime paymentTime, String userId,
            String invoiceId) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.userId = userId;
        this.invoiceId = invoiceId;
    }
    
    public PaymentDetail(String paymentId, int amount, String paymentMethod, LocalDateTime paymentTime) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
    }
    
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    public String getAmountHourString() {
        return amountHourString;
    }
    public void setAmountHourString(String amountHourString) {
        this.amountHourString = amountHourString;
    }
    

}
