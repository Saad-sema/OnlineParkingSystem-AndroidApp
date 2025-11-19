package com.example.sytemonpark;

import static android.provider.Settings.Secure.getString;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class checkAvaibility {

    private int slotId;
    private Context context;
    private AvailabilityCallback callback;

    public interface AvailabilityCallback {
        void onResult(String availability);
    }

    public checkAvaibility(int slotId, Context context, AvailabilityCallback callback) {
        this.slotId = slotId;
        this.context = context;
        this.callback = callback;
    }

    public void fetchAvailability() {
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/get_slot_timing.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() == 0) {
                            callback.onResult("Available");
                        } else {
                            callback.onResult("Occupied");
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        callback.onResult("Unknown");
                    }
                },
                error -> {
                    Toast.makeText(context, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    callback.onResult("Unknown");
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("slot_id", String.valueOf(slotId));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }
}
