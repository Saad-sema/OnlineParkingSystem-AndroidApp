package com.example.systemmanagersite;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager_booking_view extends AppCompatActivity {

    ListView listView;
    private List<view_Booking_data> bookingList =new ArrayList<>();
    view_BookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mananger_booking_view);

        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);

        adapter = new view_BookingAdapter(this, bookingList);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());
        fetchAllBooking();
    }
    private void fetchAllBooking() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/systemman.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {
                    bookingList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String userName = obj.getString("username");
                            String slotName = obj.getString("slotname");
                            String vehicleNo = obj.getString("VehicleNo");
                            String entyTime = obj.getString("starttime"); // CASE MATCHES
                            String exitTime = obj.getString("endtime");   // CASE MATCHES
                            String status = obj.getString("Status");
                            String phoneNo = obj.getString("PhoneNo");

                            bookingList.add(new view_Booking_data(userName,slotName,vehicleNo,entyTime,exitTime,status,phoneNo));
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
