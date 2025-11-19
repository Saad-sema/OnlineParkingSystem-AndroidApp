package com.example.sytemonpark;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.sytemonpark.R;
import com.google.android.material.navigation.NavigationView;
import org.json.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class User_Home_Page extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;
    private ListView listView;
    private EditText etEntryTime, etExitTime;
    private Button btnSearch;

    private List<ParkingSlot> allSlots = new ArrayList<>();
    private ParkingSlotAdapter adapter;
    SharedPreferences prefs;
    private SimpleDateFormat dateTimeFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        // Views
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listview_parking_slots);
        etEntryTime = findViewById(R.id.et_entry_time);
        etExitTime = findViewById(R.id.et_exit_time);
        btnSearch = findViewById(R.id.btn_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Home");
        View headerView = navView.getHeaderView(0);


        TextView tvUserEmail = headerView.findViewById(R.id.tv_user_email);
        TextView tvUserName = headerView.findViewById(R.id.tv_user_name);

        prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String name = prefs.getString("name", "");
        String email = prefs.getString("email", "");
        tvUserEmail.setText(email);
        tvUserName.setText(name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(User_Home_Page.this, User_Home_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else if (id == R.id.nav_your_booking) {
                startActivity(new Intent(User_Home_Page.this, User_Your_Booking_page.class));
            } else if (id == R.id.nav_booking_history) {
                startActivity(new Intent(User_Home_Page.this, User_Parking_History.class));
            }else if (id == R.id.nav_about_us) {
                startActivity(new Intent(User_Home_Page.this, activity_about_us.class));
            }
            else if (id == R.id.nav_logout) {
                SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(User_Home_Page.this,User_Login_Page.class));
            }
            drawerLayout.closeDrawers();
            return true;
        });

        adapter = new ParkingSlotAdapter(this, allSlots);
        listView.setAdapter(adapter);

        // Entry picker
        etEntryTime.setOnClickListener(v -> showDateTimePicker(etEntryTime));
        etExitTime.setOnClickListener(v -> showDateTimePicker(etExitTime));

        btnSearch.setOnClickListener(v -> {
            String entry = etEntryTime.getText().toString();
            String exit = etExitTime.getText().toString();
            if (entry.isEmpty() || exit.isEmpty()) {
                Toast.makeText(this, "Please select both Entry & Exit time", Toast.LENGTH_SHORT).show();
            } else {
                fetchParkingSlotsbyTime(entry,exit);
                Toast.makeText(this, "Searching from " + entry + " to " + exit, Toast.LENGTH_SHORT).show();
            }
        });

        fetchParkingSlots();
    }


    private void showDateTimePicker(EditText target) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    TimePickerDialog timePicker = new TimePickerDialog(this,
                            (tp, hour, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);

                                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                                    Toast.makeText(this, "Please select a future time", Toast.LENGTH_SHORT).show();
                                } else {
                                    target.setText(dateTimeFormat.format(calendar.getTime()));
                                }
                            }, calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE), true);
                    timePicker.show();

                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        
        // restrict past dates
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
        datePicker.show();
    }

    private void fetchParkingSlots() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/get_slots.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    allSlots.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String name = obj.getString("SlotNumber");
                            double fees = obj.getDouble("RatePerHour");
                            int slotId = obj.getInt("SlotID");
                            allSlots.add(new ParkingSlot(name, fees, slotId));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

        queue.add(request);
    }

    private void fetchParkingSlotsbyTime(String entry, String exit) {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/fetchBytime.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        allSlots.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String name = obj.getString("SlotNumber");
                            double fees = obj.getDouble("RatePerHour");
                            int slotId = obj.getInt("SlotID");
                            allSlots.add(new ParkingSlot(name, fees, slotId));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parse Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("entry", entry);
                params.put("exit", exit);
                return params;
            }
        };

        queue.add(request);
    }

}
