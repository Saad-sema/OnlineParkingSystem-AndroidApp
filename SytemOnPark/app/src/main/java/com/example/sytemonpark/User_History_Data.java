package com.example.sytemonpark;

public class User_History_Data {
    String userName, slotName, vehicleNo, entryTime, exitTime, paymentStatus,phoneNo;

    public User_History_Data(String userName, String slotName, String vehicleNo, String entryTime, String exitTime, String paymentStatus,String phoneNo) {
        this.userName = userName;
        this.slotName = slotName;
        this.vehicleNo = vehicleNo;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.paymentStatus = paymentStatus;
        this.phoneNo=phoneNo;
    }
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
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public String getPhoneNo(){return phoneNo;}
}

