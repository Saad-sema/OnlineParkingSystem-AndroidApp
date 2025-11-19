package com.example.systemadminsite;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Admin_User_Manage_Page extends AppCompatActivity {

    ListView listView;
    ArrayList<admin_user_data> userArrayList;
    ArrayList<admin_user_data> filteredList;
    admin_user_adapter adapter;
    ImageView btnBack,btnRef;
    TextView toolbarTitle;
    EditText searchBar;
    FloatingActionButton fabRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_manage_page);

        btnBack = findViewById(R.id.btnBack);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        searchBar = findViewById(R.id.searchBar);
        btnRef=findViewById(R.id.btnRefress);
        fabRight = findViewById(R.id.fabRight);

        fabRight.setOnClickListener(v -> showAddSlotDialog());
        btnBack.setOnClickListener(v -> onBackPressed());
        toolbarTitle.setText("User Manage");

        listView = findViewById(R.id.listView);

        userArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new admin_user_adapter(this, filteredList);
        listView.setAdapter(adapter);


        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllUsers();
            }
        });
        // Search filter
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSlots(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
        fetchAllUsers();
    }

    private void filterSlots(String query) {
        filteredList.clear();
        for (admin_user_data user : userArrayList) {
            if (user.getUserName().toLowerCase().contains(query.toLowerCase())
                    || user.getUserId().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void showAddSlotDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.user_add_dialog, null);

        EditText etManagerName = dialogView.findViewById(R.id.etManagerName);
        EditText etEmail = dialogView.findViewById(R.id.etEmail);
        EditText etPassword = dialogView.findViewById(R.id.etPassword);
        EditText etPhoneNo = dialogView.findViewById(R.id.etPhoneNo);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnDone = dialogView.findViewById(R.id.btnDone);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        dialog.show();
        dialog.getWindow().setLayout(
                (int) (this.getResources().getDisplayMetrics().widthPixels * 0.9),
                WindowManager.LayoutParams.WRAP_CONTENT
        );

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnDone.setOnClickListener(v -> {
            String managerName = etManagerName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phoneNo = etPhoneNo.getText().toString().trim();

            if (managerName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNo.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            ManageApis api = new ManageApis();
            api.addManager(this,managerName,email,password,phoneNo);
            fetchAllUsers();
            adapter.notifyDataSetChanged();
            //Toast.makeText(this, "Slot Added: " + slotName, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    public void fetchAllUsers() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    userArrayList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String userName = obj.getString("Name");
                            String email = obj.getString("Email");
                            String userId = obj.getString("UserID");
                            String phoneNo = obj.getString("Phone");
                            String status = obj.getString("Status");
                            String userPassword = obj.getString("Password");
                            String userRegistrationDate = obj.getString("RegistrationDate");

                            userArrayList.add(new admin_user_data(
                                    userId, userName, email, phoneNo, status,userPassword,userRegistrationDate
                            ));
                        }


                        filteredList.clear();
                        filteredList.addAll(userArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "fetchUsers");
                return params;
            }
        };
        queue.add(request);
    }
}
