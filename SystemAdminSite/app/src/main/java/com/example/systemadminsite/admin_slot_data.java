package com.example.systemadminsite;

public class admin_slot_data {
    private String slotId;
    private String slotName;
    private String slotLocation;
    private String rate;
    private String extraCharge;

    public admin_slot_data(String slotId, String slotName, String slotLocation, String rate,String extraCharge) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.slotLocation = slotLocation;
        this.rate = rate;
        this.extraCharge=extraCharge;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public String getSlotLocation() {
        return slotLocation;
    }

    public String getRate() {
        return rate;
    }

    public String getExtraCharge(){ return extraCharge;}
}
