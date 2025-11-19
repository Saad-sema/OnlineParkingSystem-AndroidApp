package com.example.sytemonpark;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class User_Booking_Details extends AppCompatActivity {

    EditText etUserName, etPhone, etVehicle, etEntryDateTime, etExitDateTime;
    RadioGroup rgDuration;
    RadioButton rbOneHour, rbTwoHour, rbThreeHour, rbLong;
    TextView tvTotalCharges;
    Button btnBookNow;
    LinearLayout longBookingExitContainer;

    String selectedDuration = "1 hour";
    String entryDateTime = "";
    String exitDateTime = "";
    Double totalCharges = 0.0;

    String slotName;
    Double slotFees  ;
    int slotId;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_details);

        //Booking slot Details
        Intent intent = getIntent();
        slotFees = intent.getDoubleExtra("Slot Fees",0.0);
        slotId = intent.getIntExtra("Slot Id",0);
        slotName=intent.getStringExtra("Slot Name");
        // Views initialize
        etUserName = findViewById(R.id.et_user_name);
        etPhone = findViewById(R.id.et_phone);
        etVehicle = findViewById(R.id.et_vehicle);
        etEntryDateTime = findViewById(R.id.et_entry_datetime);
        etExitDateTime = findViewById(R.id.et_exit_datetime);

        rgDuration = findViewById(R.id.rg_duration);
        rbOneHour = findViewById(R.id.rb_one_hour);
        rbTwoHour = findViewById(R.id.rb_two_hour);
        rbThreeHour = findViewById(R.id.rb_three_hour);
        rbLong = findViewById(R.id.rb_long);

        longBookingExitContainer = findViewById(R.id.long_booking_exit_container);

        tvTotalCharges = findViewById(R.id.tv_total_charges);
        btnBookNow = findViewById(R.id.btn_book_now);


        rbOneHour.setChecked(true);
        updateChargesAndExitTime();
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        String name = prefs.getString("name", "");
        etUserName.setText(name);
        etUserName.setEnabled(false);

        etEntryDateTime.setOnClickListener(v -> pickDateTime(etEntryDateTime));


        etExitDateTime.setOnClickListener(v -> pickDateTime(etExitDateTime));

        // RadioGroup for duration logic
        rgDuration.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_long) {
                longBookingExitContainer.setVisibility(LinearLayout.VISIBLE);
                selectedDuration = "Long Booking";
                etExitDateTime.setText("");
                tvTotalCharges.setText("Total Charges: ₹0");
            } else {
                longBookingExitContainer.setVisibility(LinearLayout.GONE);
                etExitDateTime.setText("");
                if (checkedId == R.id.rb_one_hour) selectedDuration = "1 hour";
                else if (checkedId == R.id.rb_two_hour) selectedDuration = "2 hour";
                else if (checkedId == R.id.rb_three_hour) selectedDuration = "3 hour";
                updateChargesAndExitTime();
            }
        });

        // Jab entry DateTime update ho (focus ya click), charges/exit bhi update karo
        etEntryDateTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && rgDuration.getCheckedRadioButtonId() != R.id.rb_long) {
                updateChargesAndExitTime();
            }
        });

        // Long booking exit change → charges update
        etExitDateTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && rgDuration.getCheckedRadioButtonId() == R.id.rb_long) {
                updateLongBookingCharges();
            }
        });

        // Book Now button validation
        btnBookNow.setOnClickListener(v -> {
            String username = etUserName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String vehicle = etVehicle.getText().toString().toUpperCase().trim();
            entryDateTime = etEntryDateTime.getText().toString().trim();
            exitDateTime = etExitDateTime.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                etUserName.setError("Please enter your name");
                etUserName.requestFocus();
                return;
            }
            if (!isValidPhone(phone)) {
                etPhone.setError("Enter valid 10-digit phone number");
                etPhone.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(vehicle)) {
                etVehicle.setError("Please enter vehicle number");
                etVehicle.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(entryDateTime)) {
                Toast.makeText(this, "Please select entry date & time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isNotPastDateTime(entryDateTime)) {
                Toast.makeText(this, "Entry date/time cannot be in the past", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDuration.equals("Long Booking")) {
                if (TextUtils.isEmpty(exitDateTime)) {
                    Toast.makeText(this, "Please select exit date & time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isExitAfterEnter(entryDateTime, exitDateTime)) {
                    Toast.makeText(this, "Exit date/time must be after entry", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Charges recalculate
            if (!selectedDuration.equals("Long Booking")) updateChargesAndExitTime();
            else updateLongBookingCharges();

            String bookingSummary = "Name: " + username + "\n"
                    + "Phone: " + phone + "\n"
                    + "Vehicle: " + vehicle + "\n"
                    + "Duration: " + selectedDuration + "\n"
                    + "Entry: " + entryDateTime + "\n"
                    + "Exit: " + (selectedDuration.equals("Long Booking") ? exitDateTime : calculateExitTime(entryDateTime, getHour(selectedDuration))) + "\n"
                    + "Total Charges: ₹" + totalCharges;

            Toast.makeText(this, bookingSummary, Toast.LENGTH_LONG).show();
            get_Booking booking = new get_Booking(this, slotId, entryDateTime, exitDateTime);

            booking.checkSlotAvailability(new get_Booking.SlotAvailabilityCallback() {
                @Override
                public void onResult(boolean isAvailable) {
                    if (isAvailable) {
                        Intent intent= new Intent(User_Booking_Details.this, User_Payment.class);
                        intent.putExtra("vehicle number",vehicle);
                        intent.putExtra("Slot Fees", totalCharges);
                        intent.putExtra("Slot Id", slotId);
                        intent.putExtra("entry time", entryDateTime);
                        intent.putExtra("exit time", exitDateTime);
                        intent.putExtra("Slot Name",slotName);
                        intent.putExtra("phoneNo",phone);
                        intent.putExtra("userName",username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(User_Booking_Details.this, "Slot is booked", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });
    }

    private void pickDateTime(EditText editText) {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.set(year, month, dayOfMonth);

                    new TimePickerDialog(this,
                            (v, hour, min) -> {
                                selectedCal.set(Calendar.HOUR_OF_DAY, hour);
                                selectedCal.set(Calendar.MINUTE, min);
                                selectedCal.set(Calendar.SECOND, 0);

                                String formattedDateTime = sdf.format(selectedCal.getTime());
                                editText.setText(formattedDateTime);

                                if (editText == etEntryDateTime && !selectedDuration.equals("Long Booking")) {
                                    updateChargesAndExitTime();
                                } else if (editText == etExitDateTime && selectedDuration.equals("Long Booking")) {
                                    updateLongBookingCharges();
                                }
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true).show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();
    }

    private void updateChargesAndExitTime() {
        int hours = getHour(selectedDuration);
        if (TextUtils.isEmpty(etEntryDateTime.getText())) {
            tvTotalCharges.setText("Total Charges: ₹0");
            totalCharges = 0.0;
            return;
        }
        totalCharges = hours * slotFees;
        tvTotalCharges.setText("Total Charges: ₹" + totalCharges);

        exitDateTime = calculateExitTime(etEntryDateTime.getText().toString(), hours);
    }

    private void updateLongBookingCharges() {
        String enter = etEntryDateTime.getText().toString();
        String exit = etExitDateTime.getText().toString();
        if (TextUtils.isEmpty(enter) || TextUtils.isEmpty(exit)) {
            tvTotalCharges.setText("Total Charges: ₹0");
            totalCharges = 0.0;
            return;
        }
        try {
            Date dEnter = sdf.parse(enter);
            Date dExit = sdf.parse(exit);
            if (dEnter != null && dExit != null && dExit.after(dEnter)) {
                long diffMs = dExit.getTime() - dEnter.getTime();
                int hours = (int) Math.ceil(diffMs / (1000.0 * 60 * 60));
                totalCharges = hours * slotFees;
                tvTotalCharges.setText("Total Charges: ₹" + totalCharges);
            } else {
                tvTotalCharges.setText("Invalid exit date/time");
                totalCharges = 0.0;
            }
        } catch (ParseException e) {
            tvTotalCharges.setText("Invalid date/time format");
            totalCharges = 0.0;
        }
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    private boolean isNotPastDateTime(String datetime) {
        try {
            Date selectedDate = sdf.parse(datetime);
            Date now = new Date();
            return selectedDate != null && !selectedDate.before(now);
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isExitAfterEnter(String enter, String exit) {
        try {
            Date dEnter = sdf.parse(enter);
            Date dExit = sdf.parse(exit);
            return dEnter != null && dExit != null && dExit.after(dEnter);
        } catch (ParseException e) {
            return false;
        }
    }

    private String calculateExitTime(String entryDateTime, int addHours) {
        try {
            Date dEntry = sdf.parse(entryDateTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dEntry);
            cal.add(Calendar.HOUR_OF_DAY, addHours);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    private int getHour(String duration) {
        if (duration.contains("1")) return 1;
        else if (duration.contains("2")) return 2;
        else if (duration.contains("3")) return 3;
        return 0;
    }
}
