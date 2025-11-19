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

public class Manager_Booking_History extends AppCompatActivity {

    ListView listView;
    private List<view_Booking_data> bookingList =new ArrayList<>();
    Manager_history_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_booking_history);

        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);

        adapter = new Manager_history_adapter(this, bookingList);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());
        fetchAllBooking("Manager_History");
    }
    private void fetchAllBooking(String action) {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/parking_history.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {
                    bookingList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String userName = obj.getString("User_Name");
                            String slotName = obj.getString("SlotName");
                            String vehicleNo = obj.getString("Vehicle_No");
                            String entyTime = obj.getString("StartTime"); // CASE MATCHES
                            String exitTime = obj.getString("EndTime");   // CASE MATCHES
                            String Payment_Amount = obj.getString("Payment_Amount");
                            String phoneNo = obj.getString("Phone_No");

                            bookingList.add(new view_Booking_data(userName,slotName,vehicleNo,entyTime,exitTime,Payment_Amount,phoneNo));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                return params;
            }
        };

        queue.add(request);
    }
}
