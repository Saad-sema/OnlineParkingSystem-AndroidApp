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

public class Admin_Slot_Manage_Page extends AppCompatActivity {

    ListView listView;
    ArrayList<admin_slot_data> slotArrayList;
    ArrayList<admin_slot_data> filteredList;
    admin_slot_adapter adapter;
    FloatingActionButton fabLeft, fabRight;
    ImageView btnBack,btnRef;
    TextView toolbarTitle;
    EditText searchBar;
    ManageApis api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_slot_manage_page);

        btnBack = findViewById(R.id.btnBack);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        searchBar = findViewById(R.id.searchBar);
        btnRef=findViewById(R.id.btnRefress);

        btnBack.setOnClickListener(v -> onBackPressed());
        toolbarTitle.setText("Parking Slots");

        listView = findViewById(R.id.listView);

        slotArrayList = new ArrayList<>();
        filteredList = new ArrayList<>();

        adapter = new admin_slot_adapter(this, filteredList);
        listView.setAdapter(adapter);

        fabLeft = findViewById(R.id.fabLeft);
        fabRight = findViewById(R.id.fabRight);

        fabLeft.setOnClickListener(v -> showAddSlotDialog());
        fabRight.setOnClickListener(v -> showUpdateDialog());
        btnRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllSlots();
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
        fetchAllSlots();
    }

    private void filterSlots(String query) {
        filteredList.clear();
        for (admin_slot_data slot : slotArrayList) {
            if (slot.getSlotName().toLowerCase().contains(query.toLowerCase())
                    || slot.getSlotLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(slot);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showUpdateDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.slot_edit_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RadioGroup rgChangeType = dialog.findViewById(R.id.rgChangeType);
        RadioGroup rgApplyType = dialog.findViewById(R.id.rgApplyType);
        TextView tvApplyTo = dialog.findViewById(R.id.tvApplyTo);
        EditText etSlotId = dialog.findViewById(R.id.etSlotId);
        EditText etValue = dialog.findViewById(R.id.etValue);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnDone = dialog.findViewById(R.id.btnDone);
        RadioButton rbRate = dialog.findViewById(R.id.rbRate);
        RadioButton rbExtraCharge = dialog.findViewById(R.id.etExtraCharges);

        rgChangeType.setOnCheckedChangeListener((group, checkedId) -> {
            tvApplyTo.setVisibility(View.VISIBLE);
            rgApplyType.setVisibility(View.VISIBLE);
            etValue.setVisibility(View.VISIBLE);

            if (checkedId == R.id.rbRate) {
                etValue.setHint("Enter Rate");
            } else if (checkedId == R.id.rbExtraCharges) {
                etValue.setHint("Enter Extra Charges");
            }
        });

        rgApplyType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAll) {
                etSlotId.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbOne) {
                etSlotId.setVisibility(View.VISIBLE);
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnDone.setOnClickListener(v -> {
            String value = etValue.getText().toString().trim();
            String slotId = etSlotId.getText().toString().trim();

            if (value.isEmpty()) {
                Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rgApplyType.getCheckedRadioButtonId() == R.id.rbOne && slotId.isEmpty()) {
                Toast.makeText(this, "Please enter Slot Id", Toast.LENGTH_SHORT).show();
                return;
            }
            api = new ManageApis();
            if(rbRate.isChecked()){
                if(rgApplyType.getCheckedRadioButtonId() == R.id.rbOne){
                    api.updateRateSlot(this,slotId,value);
                }else{
                    api.updateRateSlot(this, "0",value);
                }
                fetchAllSlots();
            }else{
                if(rgApplyType.getCheckedRadioButtonId() == R.id.rbOne){
                    api.updateExtraRateSlot(this,slotId,value);
                }else{
                    api.updateExtraRateSlot(this, "0",value);
                }
                fetchAllSlots();
            }

            //Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();

    }

    public void showAddSlotDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.slot_add_dialog, null);

        EditText etSlotName = dialogView.findViewById(R.id.etSlotName);
        EditText etSlotLocation = dialogView.findViewById(R.id.etSlotLocation);
        EditText etRate = dialogView.findViewById(R.id.etRate);
        EditText etExtraCharges = dialogView.findViewById(R.id.etExtraCharges);
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
            String slotName = etSlotName.getText().toString().trim();
            String slotLocation = etSlotLocation.getText().toString().trim();
            String rate = etRate.getText().toString().trim();
            String extra = etExtraCharges.getText().toString().trim();

            if (slotName.isEmpty() || slotLocation.isEmpty() || rate.isEmpty() || extra.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            api = new ManageApis();
                api.addSlot(this,slotName,slotLocation,rate,extra);

            //Toast.makeText(this, "Slot Added: " + slotName, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    public void fetchAllSlots() {
        String base_url=getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    slotArrayList.clear();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String slotName = obj.getString("SlotNumber");
                            String rate = obj.getString("RatePerHour");
                            String slotId = obj.getString("SlotID");
                            String extraCharge = obj.getString("ExtraCharge");
                            String slotLocation = obj.getString("Location");

                            slotArrayList.add(new admin_slot_data(
                                    slotId, slotName, slotLocation, rate, extraCharge
                            ));
                        }

                        filteredList.clear();
                        filteredList.addAll(slotArrayList);

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
                params.put("action", "fetchSlot");
                return params;
            }
        };
        queue.add(request);
    }
}
