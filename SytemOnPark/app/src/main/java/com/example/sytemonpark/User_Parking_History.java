package com.example.sytemonpark;
import android.content.SharedPreferences;
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

public class User_Parking_History extends AppCompatActivity {

    ListView listView;
    private List<User_History_Data> bookingList =new ArrayList<>();
    history_list_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_parking_history);

        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String userid = prefs.getString("userid", "");
        adapter = new history_list_Adapter(this, bookingList);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());
        fetchAllBooking("User_History",userid);
    }
    private void fetchAllBooking(String action,String Id) {
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

                            bookingList.add(new User_History_Data(userName,slotName,vehicleNo,entyTime,exitTime,Payment_Amount,phoneNo));
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
                params.put("user_id",String.valueOf(Id));
                return params;
            }
        };

        queue.add(request);
    }
}
