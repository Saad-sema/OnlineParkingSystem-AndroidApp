package com.example.sytemonpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class User_Payment extends AppCompatActivity {

    EditText etPaymentAmount, etUpiId;
    RadioGroup rgPaymentMethod;
    RadioButton rbUpi;
    Button btnPay;
    Double slotFees;
    int slotId;
    String entryTime;
    String exitTime;
    String vehicleNo;
    String userId;
    String userName;
    String phoneNo;
    String slotName;
    int verOTP;
    // Suppose payment amount is fixed from previous screen;
    // hard-coded here for demo, else use intent
    private double paymentAmount ;// set as per your logic

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);
        Intent intent = getIntent();
        slotFees = intent.getDoubleExtra("Slot Fees",0.0);
        slotId = intent.getIntExtra("Slot Id",0);
        entryTime=intent.getStringExtra("entry time");
        exitTime=intent.getStringExtra("exit time");
        vehicleNo=intent.getStringExtra("vehicle number");
        slotName=intent.getStringExtra("Slot Name");
        phoneNo=intent.getStringExtra("phoneNo");
        userName=intent.getStringExtra("userName");
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        userId = prefs.getString("userid", "");

        etPaymentAmount = findViewById(R.id.et_payment_amount);
        etUpiId = findViewById(R.id.et_upi_id);
        rgPaymentMethod = findViewById(R.id.rg_payment_method);
        rbUpi = findViewById(R.id.rb_upi);
        btnPay = findViewById(R.id.btn_pay);

        // Set fixed payment amount (disable editing)
        paymentAmount=slotFees;
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

        verOTP = (int) (100000 + Math.random() * 900000);
        btnPay.setOnClickListener(v -> {
            String upiId = etUpiId.getText().toString().trim();
            if (!isValidUpiId(upiId)) {
                etUpiId.setError("Enter valid UPI ID (e.g. example@okaxis)");
                etUpiId.requestFocus();
                return;
            }
           // Toast.makeText(this, "Payment processing: ₹" + paymentAmount + "\nUPI: " + upiId, Toast.LENGTH_SHORT).show();
            get_Booking bk=new get_Booking(this,Integer.parseInt(userId),slotId,entryTime,exitTime,vehicleNo,paymentAmount,upiId,method,userName,slotName,verOTP,phoneNo,Message -> {
                if("Succesfull".equalsIgnoreCase(Message)){
                    paymentDialog pd=new paymentDialog();
                    pd.showPaymentDialog(User_Payment.this, String.valueOf(verOTP));
                }
                else{
                    Toast.makeText(User_Payment.this,"failed",Toast.LENGTH_LONG).show();
                }
            });
            bk.do_Booking();
        });
    }

    // Basic UPI ID validation: like name@bank or mobile@bank, not comprehensive but practical
    private boolean isValidUpiId(String upiId) {
        return upiId.matches("^[\\w\\.-]{2,256}@[\\w]{2,64}$");
    }
}
