package com.rtech.jewellery.dto;

import java.math.BigDecimal;

public class MonthlySalesDto {

    private int month;
    private int year;
    private BigDecimal totalAmount;

    public MonthlySalesDto(int month, int year, BigDecimal totalAmount) {
        this.month = month;
        this.year = year;
        this.totalAmount = totalAmount;
    }

    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setMonth(int month) {
        this.month = month;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
