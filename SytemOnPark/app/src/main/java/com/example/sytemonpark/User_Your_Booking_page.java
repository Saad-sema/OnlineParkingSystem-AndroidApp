package com.example.sytemonpark;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Your_Booking_page extends AppCompatActivity {
    ListView listView;
    your_booking_adapter adapter;
    private List<your_booking_data> extendSlot =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_your_booking_page);
        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnRef=findViewById(R.id.btnRefres);
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userid = prefs.getString("userid", "");
        adapter= new your_booking_adapter(this, extendSlot);
        listView.setAdapter(adapter);
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchExtendDuration(userid);
            }
        });
        btnBack.setOnClickListener(v -> finish());



        fetchExtendDuration(userid);
    }
    private void fetchExtendDuration(String userid) {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/drawer_pages.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    extendSlot.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String userName = obj.optString("UserName");
                            String slotName = obj.optString("SlotName");
                            String vehicleNo = obj.optString("VehicleNo");
                            String entryTime = obj.optString("StartTime");
                            String exitTime = obj.optString("EndTime");
                            String status = obj.optString("Status");
                            String phoneNo = obj.optString("PhoneNo");
                            String checkOTP = obj.optString("OTP");
                            String bookingId = obj.optString("BookingID");
                            String slotId = obj.optString("SlotID");
                            String extraCharge = obj.optString("ExtraCharge", "0");
                            String PayExtraAmount = obj.optString("PayExtraAmount", "0");

                            extendSlot.add(new your_booking_data(
                                    userName, slotName, vehicleNo, entryTime, exitTime,
                                    status, phoneNo, checkOTP, bookingId, slotId, extraCharge,PayExtraAmount
                            ));
                        }
                    } catch (JSONException e) {
                        Log.e("fetchExtendDuration", "JSON Parsing error: " + e.getMessage());
                    }

                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userid);
                return params;
            }
        };

        queue.add(request);
    }

}