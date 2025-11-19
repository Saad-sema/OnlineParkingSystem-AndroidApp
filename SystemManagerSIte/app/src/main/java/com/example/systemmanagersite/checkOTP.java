package com.example.systemmanagersite;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class checkOTP {
    Context context;

    public checkOTP(Context context,String OTP,String bookingId,String action) {
        this.context=context;
        Dialog otpDialog = new Dialog(context);
        otpDialog.setContentView(R.layout.checkotp);

        EditText otpEditText = otpDialog.findViewById(R.id.otpEditText);
        TextView errorTextView = otpDialog.findViewById(R.id.errorTextView);
        Button doneBtn = otpDialog.findViewById(R.id.doneBtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = otpEditText.getText().toString();
                String CheckOTP= String.valueOf(OTP);
                if (enteredOtp.equals(CheckOTP)) {
                    giveConfirmation(bookingId,action);
                    otpDialog.dismiss();
                } else {
                    errorTextView.setText("Please enter the correct OTP");
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        otpDialog.show();
    }
    public void giveConfirmation(String bookingId,String action){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/ManagerTask.php";

            RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        boolean success = obj.getBoolean("success");
                        String message = obj.getString("message");

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                params.put("booking_id", bookingId);
                return params;
            }
        };


        queue.add(request);
    }
}
