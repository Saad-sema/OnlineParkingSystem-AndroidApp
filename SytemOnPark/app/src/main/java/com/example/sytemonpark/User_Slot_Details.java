package com.example.sytemonpark;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Slot_Details extends AppCompatActivity {

    ImageView imgSlot;
    TextView tvName, tvAvailability, tvSlotFees;
    Button btnBookNow;
    StringBuilder sltTiming = new StringBuilder();
    int slotId;
    Double slotFees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_slot_details);

        tvName = findViewById(R.id.tv_slot_name);
        tvAvailability = findViewById(R.id.tv_availability);
        tvSlotFees = findViewById(R.id.tv_slot_Fees);
        btnBookNow = findViewById(R.id.btn_book_now_detail);
        ImageSlider imageSlider = findViewById(R.id.img_slider_detail);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.imgu1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgu2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgu3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgu4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imgu5, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
        // Get data from intent (if passed)
        Intent intent = getIntent();
        String slotName = intent.getStringExtra("Slot Name");
      //  String slotStatus = intent.getStringExtra("Slot Status");
        slotFees = intent.getDoubleExtra("Slot Fees",0.0);
        slotId = intent.getIntExtra("Slot Id",0);

        fetchTiming();

        tvName.setText(slotName);
       // tvAvailability.setText(slotStatus);
        tvSlotFees.setText("\n"+"\n"+"" +"Rate Per Hour : "+slotFees);


        Intent intentB = new Intent(User_Slot_Details.this, User_Booking_Details.class);
        intentB.putExtra("Slot Name", slotName);
       // intentB.putExtra("Slot Status", slotStatus);
        intentB.putExtra("Slot Fees", slotFees);
        intentB.putExtra("Slot Id", slotId);
        // Book Now button action
        btnBookNow.setOnClickListener(v -> {
            // Open booking activity, show dialog or process booking
            Toast.makeText(this, "Booking slot: " + tvName.getText(), Toast.LENGTH_SHORT).show();

            startActivity(intentB);
        });
    }

    private void fetchTiming() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/get_slot_timing.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String entryTime = obj.getString("starttime");
                            String exitTime = obj.getString("endtime");

                            sltTiming.append(entryTime).append(" to ").append(exitTime).append("\n");
                        }
                        if(sltTiming.toString().isEmpty()) {
                            tvAvailability.setText("Available");
                        }else{
                            tvAvailability.setText("Not Available At:  " + "\n" + sltTiming.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("slot_id", String.valueOf(slotId)); // Ensure slotId is accessible
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}


