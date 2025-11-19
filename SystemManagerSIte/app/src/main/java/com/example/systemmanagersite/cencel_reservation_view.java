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

public class cencel_reservation_view extends AppCompatActivity {
    ListView listView;
    cencel_view_adapter adapter;
    private List<view_ConfRes_data> cencelSlots =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cencel_reservation_view);
        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);
        ImageView btnRef=findViewById(R.id.btnRefres);

        adapter= new cencel_view_adapter(this, cencelSlots);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCencelBooking();
            }
        });
        fetchCencelBooking();
    }
    private void fetchCencelBooking() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/view_cencel_parking.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {
                    cencelSlots.clear();
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
                            String bookingId = obj.getString("BookingID");
                            String slotId =obj.getString("SlotID");// CASE MATCHES


                            cencelSlots.add(new view_ConfRes_data(userName,slotName,vehicleNo,entyTime,exitTime,status,phoneNo,checkOTP,bookingId,slotId));
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