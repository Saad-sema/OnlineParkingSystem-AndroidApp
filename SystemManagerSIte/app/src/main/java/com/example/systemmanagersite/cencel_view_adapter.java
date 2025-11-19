package com.example.systemmanagersite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cencel_view_adapter extends BaseAdapter {
    Context context;
    List<view_ConfRes_data> bookingList;
    public cencel_view_adapter(Context context, List<view_ConfRes_data> bookingList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.cencel_view_layout, parent, false);
        }
        view_ConfRes_data booking = bookingList.get(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvSlotName = convertView.findViewById(R.id.tvSlotName);
        TextView tvVehicleNo = convertView.findViewById(R.id.tvVehicleNo);
        TextView tvEntryTime = convertView.findViewById(R.id.tvEntryTime);
        TextView tvExitTime = convertView.findViewById(R.id.tvExitTime);
        TextView tvPhoneNo = convertView.findViewById(R.id.tvPhoneNo);
        TextView tvPaymentStatus = convertView.findViewById(R.id.tvPaymentStatus);
        Button  btnConfirm = convertView.findViewById(R.id.btnExtend);
        tvUserName.setText(booking.getUserName());
        tvSlotName.setText(booking.getSlotName());
        tvVehicleNo.setText(booking.getVehicleNo());
        tvEntryTime.setText(booking.getEntryTime());
        tvExitTime.setText(booking.getExitTime());
        tvPhoneNo.setText(booking.getPhoneNo());
        tvPaymentStatus.setText(booking.getPaymentStatus());
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelOrDoneDialog(booking.getBookingId());
            }
        });

        return convertView;
    }
    private void showCancelOrDoneDialog(String bookingId){
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to cancel?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    giveCencelation(bookingId);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Handle Cancel action here, e.g., just close dialog
                    dialog.dismiss();
                })
                .setCancelable(false) // prevent dismiss by tapping outside or back button
                .show();
    }
    public void giveCencelation(String bookingId){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/ManagerTask.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "give_Cencelation");// tell PHP which action to perform
            params.put("booking_id", String.valueOf(bookingId));
            return params;
        }
        };

        queue.add(request);
    }
}
