package com.example.systemadminsite;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ADMIN_PASSWORD = "admin@123"; // demo password

    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entered = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(entered)) {
                    etPassword.setError("Password required");
                    return;
                }

                if (entered.equals(ADMIN_PASSWORD)) {
                    Intent intent = new Intent(MainActivity.this, Admin_Dashboard_Page.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                }
            }
        });
    }
}
