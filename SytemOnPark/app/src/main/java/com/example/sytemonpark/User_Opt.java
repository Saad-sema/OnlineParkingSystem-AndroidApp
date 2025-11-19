package com.example.sytemonpark;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class User_Opt extends Activity {

    private EditText etOtp;
    private Button btnVerifyOtp, btnResendOtp;

    private String sentOtpCode;    // OTP generated for demo
    private String phoneNumber;
    private String phoneReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_opt);

        etOtp = findViewById(R.id.etOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        btnResendOtp = findViewById(R.id.btnResendOtp);

        phoneNumber = getIntent().getStringExtra("PHONE_NUMBER");
        phoneReg=getIntent().getStringExtra("PHONE_REG");

        if(phoneNumber.isEmpty()){
            sendOtp(phoneReg);
        }
        else {
            sendOtp(phoneNumber);
        }
        btnVerifyOtp.setOnClickListener(v -> {
            String enteredOtp = etOtp.getText().toString().trim();
            if (TextUtils.isEmpty(enteredOtp)) {
                etOtp.setError("Please enter OTP");
                return;
            }
            if (enteredOtp.equals(sentOtpCode)) {
                Toast.makeText(this, "OTP Verified! Access granted.", Toast.LENGTH_LONG).show();
                // Proceed to main/home activity or dashboard
                // Intent intent = new Intent(this, MainActivity.class);
                // startActivity(intent);
                // finish();
            } else {
                etOtp.setError("Incorrect OTP. Please try again.");
            }
        });

        btnResendOtp.setOnClickListener(v -> {
            sendOtp(phoneNumber);
            Toast.makeText(this, "OTP Sent Again", Toast.LENGTH_SHORT).show();
        });
    }

    // Fake OTP send function (simulate real SMS sending)
    private void sendOtp(String phoneNum) {
        sentOtpCode = generateRandomOtp();
        Toast.makeText(this, "Demo OTP: " + sentOtpCode, Toast.LENGTH_LONG).show(); // Show OTP for testing only

    }

    private String generateRandomOtp() {
        int random = (int) (100000 + Math.random() * 900000);
        return String.valueOf(random);
    }
}
