package com.example.systemadminsite;

public class admin_history_data {
    String userName, slotName, vehicleNo, entryTime, exitTime, paidAmount,phoneNo;
    String bookingId,paymentId,historyId;
    public admin_history_data(String historyId,String bookingId,String paymentId,String userName, String slotName, String vehicleNo, String entryTime, String exitTime, String paidAmount,String phoneNo) {
        this.userName = userName;
        this.slotName = slotName;
        this.vehicleNo = vehicleNo;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.paidAmount = paidAmount;
        this.phoneNo=phoneNo;
        this.bookingId=bookingId;
        this.paymentId=paymentId;
        this.historyId=historyId;
    }
    public String getHistoryId(){return historyId;}
    public String getBookingId(){return bookingId;}
    public String getPaymentId(){return paymentId;}
    public String getUserName() {
        return userName;
    }
    public String getSlotName() {
        return slotName;
    }
    public String getEntryTime() {
        return entryTime;
    }
    public String getExitTime() {
        return exitTime;
    }
    public String getVehicleNo() {
        return vehicleNo;
    }
    public String getPaidAmount() {
        return paidAmount;
    }
    public String getPhoneNo(){return phoneNo;}
}
