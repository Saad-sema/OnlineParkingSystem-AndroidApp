package com.example.systemmanagersite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

public class extend_free_slot_adapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> slotList;
    String exitTime;
    private LayoutInflater inflater;
    Integer ExtraCharge;
    String bookingId;

    public extend_free_slot_adapter(Context context,ArrayList<HashMap<String, String>> slotList,String exitTime,Integer ExtraCharge,String bookingId) {
        this.context = context;
        this.slotList = slotList;
        this.inflater = LayoutInflater.from(context);
        this.exitTime=exitTime;
        this.ExtraCharge=ExtraCharge;
        this.bookingId=bookingId;
    }

    @Override
    public int getCount() {
        return slotList.size();
    }

    @Override
    public Object getItem(int position) {
        return slotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        Button btnThis;
        ImageView imgSlot;
        TextView tvSlotName;

        TextView tvfees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.free_slot_layout, parent, false);
            holder = new ViewHolder();
            holder.imgSlot = convertView.findViewById(R.id.img_slot);
            holder.tvSlotName = convertView.findViewById(R.id.tv_slot_name);
            holder.btnThis = convertView.findViewById(R.id.btnThis);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> slot = slotList.get(position);
        holder.tvSlotName.setText(slot.get("SlotName"));
       holder.btnThis.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                callExtendBookingApi(slot.get("SlotId"),bookingId,exitTime,ExtraCharge,slot.get("SlotName"));
           }
       });

        return convertView;
    }
    private void callExtendBookingApi(String slotId, String bookingId, String newEndTime, Integer extraCharge,String slotName) {
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/change_slot.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle JSON response
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                         if (status.equals("updated")) {
                            // Show success dialog
                            new AlertDialog.Builder(context)
                                    .setTitle("Booking Extended")
                                    .setMessage("Parking booking extended successfully.")
                                    .setPositiveButton("Done", (dialog, which) -> {
                                        Intent intent = new Intent(context,Manager_extend_site.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        context.startActivity(intent);
                                    })
                                    .show();
                        } else {
                            Toast.makeText(context, "Status: " + status, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "JSON Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("slotId", slotId);
                params.put("bookingId", bookingId);
                params.put("newEndTime", newEndTime);
                params.put("extraCharge", String.valueOf(extraCharge));
                params.put("slotName",slotName);
                return params;
            }
        };

        queue.add(request);
    }
}
