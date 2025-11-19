package com.example.systemadminsite;

public class admin_payment_data {
    String paymentTime, paymentMethod, amount,status,extraCharge;
    String bookingId,paymentId,userId;
    public admin_payment_data(String userId,String bookingId,String paymentId, String paymentTime, String paymentMethod, String amount,String status,String extraCharge) {
        this.paymentTime = paymentTime;
        this.paymentMethod=paymentMethod;
        this.amount = amount;
        this.status=status;
        this.extraCharge=extraCharge;
        this.bookingId=bookingId;
        this.paymentId=paymentId;
        this.userId=userId;
    }
    public String getUserId(){return userId;}
    public String getBookingId(){return bookingId;}
    public String getPaymentId(){return paymentId;}
    public String getPaymentTime() {
        return paymentTime;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public String getStatus() {
        return status;
    }
    public String getAmount() {
        return amount;
    }
    public String getExtraCharge(){return extraCharge;}
}
