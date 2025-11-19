package com.example.systemadminsite;

public class Admin_Booking_Data {
    String userName, slotName, vehicleNo, entryTime, exitTime, paymentStatus,phoneNo;
    String checkOTP,bookingId,slotId,ExtraCharge,paymentId;


    public Admin_Booking_Data(String userName, String slotName, String vehicleNo, String entryTime, String exitTime, String paymentStatus,String phoneNo,String checkOTP,String bookingId,String slotId,String paymentId) {
        this.userName = userName;
        this.slotName = slotName;
        this.slotId=slotId;
        this.vehicleNo = vehicleNo;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.paymentStatus = paymentStatus;
        this.phoneNo=phoneNo;
        this.checkOTP=checkOTP;
        this.bookingId=bookingId;
        this.paymentId=paymentId;
    }

    public String getUserName() {
        return userName;
    }
    public String getSlotName() {
        return slotName;
    }
    public String getSlotId(){return slotId;}
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
    public String getOTP(){return checkOTP; }
    public String getBookingId(){return bookingId;}
    public String getPaymentId(){return paymentId;}
    public String getExtraCharge(){return ExtraCharge;}
}


