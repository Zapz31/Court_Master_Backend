package com.swp391.Court_Master.Entities.QueryDashBoardMapper;

import java.time.LocalDateTime;

public class QueryTotalRevenueMapper {
    private LocalDateTime paymentTime;
    private int amount;
    public QueryTotalRevenueMapper() {
    }
    public QueryTotalRevenueMapper(LocalDateTime paymentTime, int amount) {
        this.paymentTime = paymentTime;
        this.amount = amount;
    }
    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    
}
