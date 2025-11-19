package com.example.sytemonpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class user_extend_free_slot extends AppCompatActivity {
    ListView listView;
    user_free_slot_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_extend_free_slot);
        listView = findViewById(R.id.listView);
        ImageView btnBack = findViewById(R.id.btnBack);
        Intent intent = getIntent();
        String exitTime=intent.getStringExtra("exitTime");
        Integer ExtraCharge=intent.getIntExtra("ExtraCharge",0);
        String bookingId=intent.getStringExtra("bookingId");
        ArrayList<HashMap<String, String>> slotData = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("slotData");
        adapter= new user_free_slot_adapter(this,slotData,exitTime,ExtraCharge,bookingId);
        listView.setAdapter(adapter);
        btnBack.setOnClickListener(v -> finish());
    }
}