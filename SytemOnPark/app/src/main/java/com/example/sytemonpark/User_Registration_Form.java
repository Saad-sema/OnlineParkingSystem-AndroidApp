package com.example.sytemonpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Registration_Form extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPhone, etPassword, etConfirmPassword;
    Button btnRegister;
    TextView tvSignIn;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, User_Home_Page.class));
            finish();
        }

        setContentView(R.layout.activity_user_registration_form);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnRegister.setOnClickListener(v -> registerUser());
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(this, User_Login_Page.class));
        });
    }

    private void registerUser() {
        String fname = etFirstName.getText().toString().trim();
        String lname = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        String cpass = etConfirmPassword.getText().toString().trim();

        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(cpass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        String base_url=getResources().getString(R.string.url);
        String url = base_url+"/parking_system/user_registration.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.getBoolean("success")) {
                            JSONObject user = obj.getJSONObject("user");

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userid", user.getString("userid"));
                            editor.putString("name", user.getString("username"));
                            editor.putString("email", user.getString("email"));
                            editor.putString("role", user.getString("role"));
                            editor.putString("phone", user.getString("phone"));
                            editor.putString("status", user.getString("status"));
                            editor.apply();

                            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, User_Home_Page.class));
                            finish();
                        } else {
                            Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", pass);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}

