package com.example.systemadminsite;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Admin_Dashboard_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ManageApis api;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView profileName, profileEmail,etAvctiveUsers,etTotalSlots,etTotalEarnings,etCompleteBookings,etActiveBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard_page);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        etAvctiveUsers = findViewById(R.id.tvActiveUsers);
        etTotalSlots=findViewById(R.id.tvTotalSlots);
        etTotalEarnings=findViewById(R.id.tvTotalEarnings);
        etCompleteBookings=findViewById(R.id.tvCompleteBookings);
        etActiveBookings=findViewById(R.id.tvActiveBookings);

        // Drawer Toggle (hamburger menu)
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation Listener
        navigationView.setNavigationItemSelectedListener(this);

        // Access Drawer Header
        profileName = navigationView.getHeaderView(0).findViewById(R.id.profile_name);
        profileEmail = navigationView.getHeaderView(0).findViewById(R.id.profile_email);

        // Example: set user details dynamically
        profileName.setText("Admin User");
        profileEmail.setText("admin@example.com");
        api =new ManageApis();
        api.deshboardRec(this, "fetchUsers", new ManageApis.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
               etAvctiveUsers.setText(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        api.deshboardRec(this, "fetchSlots", new ManageApis.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                etTotalSlots.setText(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        api.deshboardRec(this, "fetchBookings", new ManageApis.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                etActiveBookings.setText(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        api.deshboardRec(this, "fetchEarnings", new ManageApis.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                etTotalEarnings.setText("₹"+result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        api.deshboardRec(this, "fetchCompleteBookings", new ManageApis.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                etCompleteBookings.setText(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Toast.makeText(this, "Dashboard Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_user) {
            Intent intent= new Intent(this, Admin_User_Manage_Page.class);
            startActivity(intent);
            Toast.makeText(this, "User Manage Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slot) {
            Intent intent= new Intent(this, Admin_Slot_Manage_Page.class);
            startActivity(intent);
            Toast.makeText(this, "Slot Manage Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_booking) {
            Intent intent= new Intent(this, admin_booking_manage_page.class);
            startActivity(intent);
            Toast.makeText(this, "Booking Manage Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_payment) {
            Intent intent= new Intent(this, Admin_Payment_Manage_Page.class);
            startActivity(intent);
            Toast.makeText(this, "Payment Manage Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_history) {
            Intent intent= new Intent(this, Admin_History_Manage_Page.class);
            startActivity(intent);
            Toast.makeText(this, "History Manage Clicked", Toast.LENGTH_SHORT).show();
        }

        // Close drawer after click
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
