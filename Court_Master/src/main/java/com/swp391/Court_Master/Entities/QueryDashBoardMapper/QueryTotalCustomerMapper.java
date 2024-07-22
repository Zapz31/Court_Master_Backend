package com.swp391.Court_Master.Entities.QueryDashBoardMapper;

public class QueryTotalCustomerMapper {
    private int month;
    private int numberOfCustomer;
    public QueryTotalCustomerMapper() {
    }

    
    public QueryTotalCustomerMapper(int month, int numberOfCustomer) {
        this.month = month;
        this.numberOfCustomer = numberOfCustomer;
    }


    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getNumberOfCustomer() {
        return numberOfCustomer;
    }
    public void setNumberOfCustomer(int numberOfCustomer) {
        this.numberOfCustomer = numberOfCustomer;
    }

    
}
