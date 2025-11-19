package com.example.sytemonpark;

public class ParkingSlot {
    private String name;
    private String status;
    private Double fees;
    private int slotId;

    public ParkingSlot(String name,Double fees,int slot_id) {
        this.name = name;

        this.fees = fees;
        this.slotId=slot_id;
    }

    public String getName() {
        return name;
    }

  //  public String getStatus() {
  //  return status;
//}
    public Double getFees() {
        return fees;
    }
    public int getSlotId() {
        return slotId;
    }
}

    //public String getImageUrl() {
//   return imageUrl;
   // }
//}

