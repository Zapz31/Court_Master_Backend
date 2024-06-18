package com.swp391.Court_Master.dto.request.Respone;

import java.time.LocalDateTime;

public class CustomerFeedback {
    private String customerName;
    private String customerImageBase64;
    private String comment;
    private int ratingPoint;
    private LocalDateTime ratingDateTime;
    public CustomerFeedback() {
    }
    public CustomerFeedback(String customerName, String customerImageBase64, String comment, int ratingPoint,
            LocalDateTime ratingDateTime) {
        this.customerName = customerName;
        this.customerImageBase64 = customerImageBase64;
        this.comment = comment;
        this.ratingPoint = ratingPoint;
        this.ratingDateTime = ratingDateTime;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerImageBase64() {
        return customerImageBase64;
    }
    public void setCustomerImageBase64(String customerImageBase64) {
        this.customerImageBase64 = customerImageBase64;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getRatingPoint() {
        return ratingPoint;
    }
    public void setRatingPoint(int ratingPoint) {
        this.ratingPoint = ratingPoint;
    }
    public LocalDateTime getRatingDateTime() {
        return ratingDateTime;
    }
    public void setRatingDateTime(LocalDateTime ratingDateTime) {
        this.ratingDateTime = ratingDateTime;
    }

    
}
