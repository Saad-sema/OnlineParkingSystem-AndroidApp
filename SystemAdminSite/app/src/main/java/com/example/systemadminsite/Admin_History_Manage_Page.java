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

public class Admin_History_Manage_Page extends AppCompatActivity {

    ListView listView;
    ArrayList<admin_history_data> bookingArrayList;
    ArrayList<admin_history_data> filteredList;
    admin_history_adapter adapter;
    ImageView btnBack,btnRef;
    TextView toolbarTitle;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history_manage_page);

        btnBack = findViewById(R.id.btnBack);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        searchBar = findViewById(R.id.searchBar);
        btnRef=findViewById(R.id.btnRefress);

        btnBack.setOnClickListener(v -> onBackPressed());
        toolbarTitle.setText("Manage History");

        listView = findViewById(R.id.listView);

        bookingArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new admin_history_adapter(this, filteredList);
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
        for (admin_history_data Booking : bookingArrayList) {
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
                            String userName = obj.getString("userId");
                            String slotName = obj.getString("SlotName");
                            String vehicleNo = obj.getString("Vehicle_No");
                            String entyTime = obj.getString("StartTime"); // CASE MATCHES
                            String exitTime = obj.getString("EndTime");   // CASE MATCHES
                            String paidAmount = obj.getString("Payment_Amount");
                            String phoneNo = obj.getString("Phone_No");
                            String bookingId = obj.getString("bookingId");
                            String paymentId =obj.getString("paymentId");
                            String historyId=obj.getString("b_history_id");

                            bookingArrayList.add(new admin_history_data(historyId,bookingId,paymentId,userName,slotName,vehicleNo,entyTime,exitTime,paidAmount,phoneNo));
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
                params.put("action", "fetchHistory");
                return params;
            }
        };
        queue.add(request);
    }
}
