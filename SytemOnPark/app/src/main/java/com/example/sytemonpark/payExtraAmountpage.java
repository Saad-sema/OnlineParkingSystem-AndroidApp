package com.example.sytemonpark;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class payExtraAmountpage extends AppCompatActivity {

    EditText etPaymentAmount, etUpiId;
    RadioGroup rgPaymentMethod;
    RadioButton rbUpi;
    Button btnPay;

    // Suppose payment amount is fixed from previous screen;
    // hard-coded here for demo, else use intent
    private String paymentAmount ;// set as per your logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);
        Intent intent = getIntent();
        String  payExtraAmount = intent.getStringExtra("payExtraAmount");
        String bookingId = intent.getStringExtra("bookingId");

        etPaymentAmount = findViewById(R.id.et_payment_amount);
        etUpiId = findViewById(R.id.et_upi_id);
        rgPaymentMethod = findViewById(R.id.rg_payment_method);
        rbUpi = findViewById(R.id.rb_upi);
        btnPay = findViewById(R.id.btn_pay);

        // Set fixed payment amount (disable editing)
        paymentAmount=payExtraAmount;
        etPaymentAmount.setText(String.valueOf(paymentAmount));
        etPaymentAmount.setEnabled(false);

        String method = rbUpi.getText().toString().trim();
        // Enable Pay button only on valid UPI id
        btnPay.setEnabled(false);

        etUpiId.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnPay.setEnabled(isValidUpiId(s.toString()));
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnPay.setOnClickListener(v -> {
            String upiId = etUpiId.getText().toString().trim();
            if (!isValidUpiId(upiId)) {
                etUpiId.setError("Enter valid UPI ID (e.g. example@okaxis)");
                etUpiId.requestFocus();
                return;
            }
            callPaymentBookingApi(paymentAmount,bookingId);
        });
    }

    // Basic UPI ID validation: like name@bank or mobile@bank, not comprehensive but practical
    private boolean isValidUpiId(String upiId) {
        return upiId.matches("^[\\w\\.-]{2,256}@[\\w]{2,64}$");
    }
    private void callPaymentBookingApi(String extraCharge, String bookingId) {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/ExtrachargePay.php";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                        if (status.equals("updated")) {
                            // Show success dialog
                            new AlertDialog.Builder(this)
                                    .setTitle("Booking Extended")
                                    .setMessage("Your Payment Successfully Done.")
                                    .setPositiveButton("Done", (dialog, which) -> {
                                        Intent intent = new Intent(this,User_Your_Booking_page.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    })
                                    .show();
                        } else {
                            Toast.makeText(this, "Status: " + status, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "JSON Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bookingId", String.valueOf(bookingId));
                params.put("extraCharge", extraCharge);
                return params;
            }
        };

        queue.add(request);
    }
}
