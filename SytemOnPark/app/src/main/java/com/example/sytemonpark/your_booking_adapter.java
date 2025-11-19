package com.example.sytemonpark;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class your_booking_adapter extends BaseAdapter {
    Context context;
    List<your_booking_data> bookingList;
    long[] totalPrice = {50};
    int ExtraCharge;

    public your_booking_adapter(Context context, List<your_booking_data> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.your_booking_layout, parent, false);
        }
        your_booking_data booking = bookingList.get(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvSlotName = convertView.findViewById(R.id.tvSlotName);
        TextView tvVehicleNo = convertView.findViewById(R.id.tvVehicleNo);
        TextView tvEntryTime = convertView.findViewById(R.id.tvEntryTime);
        TextView tvExitTime = convertView.findViewById(R.id.tvExitTime);
        TextView tvPhoneNo = convertView.findViewById(R.id.tvPhoneNo);
        TextView tvPaymentStatus = convertView.findViewById(R.id.tvPaymentStatus);
        Button btnExtend = convertView.findViewById(R.id.btnExtend);

        tvUserName.setText(booking.getUserName());
        tvSlotName.setText(booking.getSlotName());
        tvVehicleNo.setText(booking.getVehicleNo());
        tvEntryTime.setText(booking.getEntryTime());
        tvExitTime.setText(booking.getExitTime());
        tvPhoneNo.setText(booking.getPhoneNo());
        tvPaymentStatus.setText(booking.getPaymentStatus());
        ExtraCharge = Integer.parseInt(booking.getExtraCharge());
        if(booking.getPaymentStatus().equals("Pending")){
            btnExtend.setText("PAY NOW");
            btnExtend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,payExtraAmountpage.class);
                    intent.putExtra("bookingId",booking.getBookingId());
                    intent.putExtra("payExtraAmount",booking.getPayExtraAmount());
                    context.startActivity(intent);
                }
            });
        }
        else {
            btnExtend.setText("EXTEND");
            btnExtend.setOnClickListener(v -> {
                showExtendOptionsDialog(booking.getExitTime(), booking.getSlotId(), booking.getBookingId());
            });
        }
        return convertView;
    }

    private void showExtendOptionsDialog(String currentEndTimeStr, String slotId, String bookingId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_your_extend_booking, null);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton radioOneHour = view.findViewById(R.id.radioOneHour);
        RadioButton radioCustom = view.findViewById(R.id.radioCustom);
        Button btnPickDateTime = view.findViewById(R.id.btnPickDateTime);
        Button btnextend = view.findViewById(R.id.btnExtend);
        TextView textPrice = view.findViewById(R.id.textPrice);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Select Extend Option")
                .setView(view)
                .setCancelable(false)
                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                .create();

        final Calendar[] selectedDateTime = {Calendar.getInstance()};
        final String[] newEndTimeFormatted = {""}; // for final use

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCustom) {
                btnPickDateTime.setVisibility(View.VISIBLE);
            } else {
                btnPickDateTime.setVisibility(View.GONE);
                totalPrice[0] = ExtraCharge;
                newEndTimeFormatted[0] = addOneHour(currentEndTimeStr);
                textPrice.setText("Total Cost: ₹50\nExtend till: " + newEndTimeFormatted[0]);
            }
        });

        btnPickDateTime.setOnClickListener(v -> {
            showDateTimePicker(dateTime -> {
                selectedDateTime[0] = dateTime;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                newEndTimeFormatted[0] = sdf.format(dateTime.getTime());

                try {
                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date original = parser.parse(currentEndTimeStr);
                    Date selected = parser.parse(newEndTimeFormatted[0]);

                    long diffMillis = selected.getTime() - original.getTime();
                    long hours = (long) Math.ceil(diffMillis / (1000.0 * 60 * 60));

                    if (hours <= 0) {
                        textPrice.setText("Invalid time selected.");
                        totalPrice[0] = 0;
                        return;
                    }

                    totalPrice[0] = hours * ExtraCharge;
                    textPrice.setText("Extend till: " + newEndTimeFormatted[0] +
                            "\nTotal Hours: " + hours +
                            "\nTotal Cost: ₹" + totalPrice[0]);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        });

        btnextend.setOnClickListener(v -> {
            if (newEndTimeFormatted[0].isEmpty()) {
                Toast.makeText(context, "Please select an option", Toast.LENGTH_SHORT).show();
                return;
            }

            callExtendBookingApi(slotId, bookingId, newEndTimeFormatted[0], totalPrice[0]);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void callExtendBookingApi(String slotId, String bookingId, String newEndTime, long extraCharge) {
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/ManagerTask.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle JSON response
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                        if (status.equals("available")) {
                            JSONArray slots = jsonObject.getJSONArray("slots");
                            ArrayList<HashMap<String, String>> availableSlots = new ArrayList<>();

                            for (int i = 0; i < slots.length(); i++) {
                                JSONObject slot = slots.getJSONObject(i);
                                HashMap<String, String> map = new HashMap<>();
                                map.put("SlotName", slot.getString("SlotNumber"));
                                map.put("SlotId", slot.getString("SlotID"));
                                availableSlots.add(map);
                            }

                            Intent intent = new Intent(context, user_extend_free_slot.class);
                            intent.putExtra("slotData", availableSlots);
                            intent.putExtra("exitTime", newEndTime);
                            intent.putExtra("bookingId", bookingId);
                            intent.putExtra("ExtraCharge", Integer.parseInt(String.valueOf(extraCharge)));
                            context.startActivity(intent);

                        } else if (status.equals("updated")) {
                            new AlertDialog.Builder(context)
                                    .setTitle("Booking Extended")
                                    .setMessage("Parking booking extended successfully.")
                                    .setPositiveButton("Done", (dialog, which) -> dialog.dismiss())
                                    .show();
                        } else {
                            Toast.makeText(context, "Status: " + status, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "JSON Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("slotId", slotId);
                params.put("bookingId", bookingId);
                params.put("newEndTime", newEndTime);
                params.put("extraCharge", String.valueOf(extraCharge));
                params.put("action", "update_Extend");
                return params;
            }
        };

        queue.add(request);
    }

    private String addOneHour(String timeStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = sdf.parse(timeStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return timeStr;
        }
    }

    // ✅ Updated function - Only future date/time allowed
    private void showDateTimePicker(DateTimeCallback callback) {
        Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, month);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                date.set(Calendar.SECOND, 0);

                // ✅ Future check
                if (date.before(Calendar.getInstance())) {
                    Toast.makeText(context, "Please select future date & time", Toast.LENGTH_SHORT).show();
                    return;
                }

                callback.onDateTimeSelected(date);

            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true);

            timePickerDialog.show();

        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));

        // ✅ Past date disable
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    interface DateTimeCallback {
        void onDateTimeSelected(Calendar selectedDateTime);
    }

}
