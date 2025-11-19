package com.example.systemadminsite;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class admin_booking_manage_page extends AppCompatActivity {

    ListView listView;
    ArrayList<Admin_Booking_Data> bookingArrayList;
    ArrayList<Admin_Booking_Data> filteredList;
    Admin_Booking_Adapter adapter;
    ImageView btnBack,btnRef;
    TextView toolbarTitle;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_booking_manage_page);

        btnBack = findViewById(R.id.btnBack);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        searchBar = findViewById(R.id.searchBar);
        btnRef=findViewById(R.id.btnRefress);

        btnBack.setOnClickListener(v -> onBackPressed());
        toolbarTitle.setText("Manage Bookind's");

        listView = findViewById(R.id.listView);

        bookingArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new Admin_Booking_Adapter(this, filteredList);
        listView.setAdapter(adapter);


        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllBooking();
            }
        });
        // Search filter
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSlots(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        fetchAllBooking();
    }

    private void filterSlots(String query) {
        filteredList.clear();
        for (Admin_Booking_Data Booking : bookingArrayList) {
            if (Booking.getUserName().toLowerCase().contains(query.toLowerCase())
                    || Booking.getBookingId().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(Booking);
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void fetchAllBooking() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    bookingArrayList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String userName = obj.getString("UserID");
                            String slotName = obj.getString("SlotName");
                            String vehicleNo = obj.getString("VehicleNo");
                            String entyTime = obj.getString("StartTime");
                            String exitTime = obj.getString("EndTime");
                            String status = obj.getString("Status");
                            String phoneNo = obj.getString("PhoneNo");
                            String checkOTP = obj.getString("OTP");
                            String bookingId = obj.getString("BookingID");
                            String slotId =obj.getString("SlotID");
                            String paymentId =obj.getString("PaymentId");

                            bookingArrayList.add(new Admin_Booking_Data(
                                    userName,slotName,vehicleNo,entyTime,exitTime,status,phoneNo,checkOTP,bookingId,slotId,paymentId
                            ));
                        }

                        filteredList.clear();
                        filteredList.addAll(bookingArrayList);

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
                params.put("action", "fetchBooking");
                return params;
            }
        };
        queue.add(request);
    }
}
