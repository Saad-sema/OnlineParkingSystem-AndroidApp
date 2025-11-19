package com.example.systemmanagersite;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manage_ConfirmReservation_view extends AppCompatActivity {
    ListView listView;
    view_ConfResAdapter adapter;
    private List<view_ConfRes_data> confirmResSlots =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_confirm_reservation_view);
        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnRef=findViewById(R.id.btnRefres);

        adapter= new view_ConfResAdapter(this, confirmResSlots);
        listView.setAdapter(adapter);
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fetchConfirmingBooking();
            }
        });
        btnBack.setOnClickListener(v -> finish());
        fetchConfirmingBooking();
    }
    private void fetchConfirmingBooking() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/view_need%20_confrim.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {
                    confirmResSlots.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String userName = obj.getString("UserName");
                            String slotName = obj.getString("SlotName");
                            String vehicleNo = obj.getString("VehicleNo");
                            String entyTime = obj.getString("StartTime"); // CASE MATCHES
                            String exitTime = obj.getString("EndTime");   // CASE MATCHES
                            String status = obj.getString("Status");
                            String phoneNo = obj.getString("PhoneNo");
                            String checkOTP = obj.getString("OTP");       // OTP is a string in your JSON!
                            String bookingId = obj.getString("BookingID"); // CASE MATCHES
                            String slotId =obj.getString("SlotID");

                            confirmResSlots.add(new view_ConfRes_data(userName,slotName,vehicleNo,entyTime,exitTime,status,phoneNo,checkOTP,bookingId,slotId));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        queue.add(request);
    }
}