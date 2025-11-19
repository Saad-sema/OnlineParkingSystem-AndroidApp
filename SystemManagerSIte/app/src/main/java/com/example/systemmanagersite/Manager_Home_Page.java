package com.example.systemmanagersite; // Your package name

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Manager_Home_Page extends AppCompatActivity {

    private ImageView profileImageView;
    private LinearLayout managerPopupLayout;
    private View overlayView;
    private Button logoutButton;
    private TextView managerIdTextView, managerNameTextView;
    LinearLayout viewBooking;
    SharedPreferences prefs;
    LinearLayout viewConfirmRes,viewCencelLate,viewExtendDuration,viewAtComp,viewHistory;

    // Assuming you have other views like the containers
    private LinearLayout pendingBookingsContainer1; // Example for one of the containers
    // Add other LinearLayouts for your clickable containers here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure 'R.layout.activity_manager_home_page' matches the filename of the XML you provided
        setContentView(R.layout.activity_manager_home_page);

        profileImageView = findViewById(R.id.profileImage);
        managerPopupLayout = findViewById(R.id.managerPopup);
        overlayView = findViewById(R.id.overlayView);
        logoutButton = findViewById(R.id.logoutButton);
        managerIdTextView = findViewById(R.id.managerId);
        managerNameTextView = findViewById(R.id.managerName);
        viewBooking =findViewById(R.id.btnViewBooking);
        viewConfirmRes=findViewById(R.id.btnConfirmReservation);
        viewCencelLate=findViewById(R.id.btnExtend);
        viewExtendDuration=findViewById(R.id.btnExtendDuration);
        viewAtComp=findViewById(R.id.btnATComp);
        viewHistory=findViewById(R.id.btnHistory);
        prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        String actualManagerName = prefs.getString("name", "");
        String actualManagerId = prefs.getString("email", "");

        if (profileImageView != null) {
            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (managerPopupLayout.getVisibility() == View.VISIBLE) {
                        managerPopupLayout.setVisibility(View.GONE);
                        overlayView.setVisibility(View.GONE);
                    } else {
                        managerPopupLayout.setVisibility(View.VISIBLE);
                        overlayView.setVisibility(View.VISIBLE);

                        managerIdTextView.setText("Manager Id :- " + actualManagerId);
                         managerNameTextView.setText("Manager Name :- " + actualManagerName);
                    }
                }
            });
        } else {
            Log.e("ManagerHomePage", "profileImageView not found! Check ID and layout inflation.");
            // It's good to know if a crucial view isn't found
            Toast.makeText(this, "Error: Profile image view not found.", Toast.LENGTH_LONG).show();
        }

        if (overlayView != null) {
            overlayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Hide popup when overlay is clicked
                    managerPopupLayout.setVisibility(View.GONE);
                    overlayView.setVisibility(View.GONE);
                }
            });
        }

        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(Manager_Home_Page.this,Managet_Login_Page.class);
                    startActivity(intent);
                    Toast.makeText(Manager_Home_Page.this, "Logout...", Toast.LENGTH_SHORT).show();

                }
            });
        }
        viewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this,Manager_booking_view.class);
                startActivity(intent);
            }
        });
        viewConfirmRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this, Manage_ConfirmReservation_view.class);
                startActivity(intent);
            }
        });
        viewExtendDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this,Manager_extend_site.class);
                startActivity(intent);
            }
        });
        viewCencelLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this,cencel_reservation_view.class);
                startActivity(intent);
            }
        });
        viewAtComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this, Manager_CompleteBooking.class);
                startActivity(intent);
            }
        });
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Manager_Home_Page.this, Manager_Booking_History.class);
                startActivity(intent);
            }
        });
    }
}