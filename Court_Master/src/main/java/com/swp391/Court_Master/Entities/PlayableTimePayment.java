package com.swp391.Court_Master.Entities;

import java.time.LocalDateTime;

public class PlayableTimePayment {
    private String paymentId;
    private int amount;
    private int minuteAmount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String customerId;
    private String badmintonClubId;
    private String badmintonClubName;
    private String playHoursMinuteString;
    private int flexiblePricePerHour;
    public PlayableTimePayment() {
    }
    public PlayableTimePayment(String paymentId, int amount, int minuteAmount, String paymentMethod,
            LocalDateTime paymentTime, String badmintonClubName) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.minuteAmount = minuteAmount;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
        this.badmintonClubName = badmintonClubName;
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
    public int getMinuteAmount() {
        return minuteAmount;
    }
    public void setMinuteAmount(int minuteAmount) {
        this.minuteAmount = minuteAmount;
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
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getBadmintonClubId() {
        return badmintonClubId;
    }
    public void setBadmintonClubId(String badmintonClubId) {
        this.badmintonClubId = badmintonClubId;
    }
    public String getBadmintonClubName() {
        return badmintonClubName;
    }
    public void setBadmintonClubName(String badmintonClubName) {
        this.badmintonClubName = badmintonClubName;
    }
    public String getPlayHoursMinuteString() {
        return playHoursMinuteString;
    }
    public void setPlayHoursMinuteString(String playHoursMinuteString) {
        this.playHoursMinuteString = playHoursMinuteString;
    }
    public int getFlexiblePricePerHour() {
        return flexiblePricePerHour;
    }
    public void setFlexiblePricePerHour(int flexiblePricePerHour) {
        this.flexiblePricePerHour = flexiblePricePerHour;
    }
}
