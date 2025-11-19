package com.example.sytemonpark;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class get_Booking {
    private String entryTime;
    private String exitTime;
    private int slotId;
    private Context context;
    private int userId;
    private String vehicleNo;
    private String upiId;
    private String method;
    private Double amount;
    private String userName;
    private String slotName;
    private int verOTP;
    public String result;
    public String phoneNo;
    private getMessageCallback callback;
     // Replace with your real URL

    public get_Booking(Context context, int slotId, String entryTime, String exitTime) {
        this.context = context;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
    public void checkSlotAvailability(SlotAvailabilityCallback callback) {
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/get_booking.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean available = obj.getBoolean("availability");
                        callback.onResult(available);
                    } catch (JSONException e) {
                        Toast.makeText(context, "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        callback.onResult(false);
                    }
                },
                error -> {
                    Toast.makeText(context, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    callback.onResult(false);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "check_availability");
                params.put("slot_id", String.valueOf(slotId));
                params.put("entry_time", entryTime);
                params.put("exit_time", exitTime);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    public interface SlotAvailabilityCallback {
        void onResult(boolean isAvailable);
    }
    public get_Booking(Context context,int userId, int slotId, String entryTime, String exitTime,String vehicleNo,Double amount,String upiId,String method,String userName,String slotName,int verOTP,String phoneNo,getMessageCallback callback){
        this.context = context;
        this.slotId = slotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.userId=userId;
        this.vehicleNo=vehicleNo;
        this.upiId=upiId;
        this.method=method;
        this.amount=amount;
        this.userName=userName;
        this.slotName=slotName;
        this.verOTP=verOTP;
        this.phoneNo=phoneNo;
        this.callback =callback;
    }
    public void do_Booking(){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/get_booking.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean success = obj.getBoolean("success");
                        String message = obj.getString("message");
                        if(success){
                            Toast.makeText(context,"Succesfully Booked",Toast.LENGTH_LONG).show();
                            callback.onResult("Succesfull");
                        }else {
                            Toast.makeText(context," Fail Booked",Toast.LENGTH_LONG).show();
                            callback.onResult("Failed");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(context, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "book_slot");               // tell PHP which action to perform
                params.put("user_id", String.valueOf(userId));
                params.put("slot_id", String.valueOf(slotId));
                params.put("entry_time", entryTime);
                params.put("exit_time", exitTime);
                params.put("vehicle_number", vehicleNo);
                params.put("amount", String.valueOf(amount));
                params.put("method", method);
                params.put("user_name",userName);
                params.put("slot_name",slotName);
                params.put("verOTP",String.valueOf(verOTP));
                params.put("phoneNo",phoneNo);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    public interface getMessageCallback{
        void onResult(String Message);
    }
}
