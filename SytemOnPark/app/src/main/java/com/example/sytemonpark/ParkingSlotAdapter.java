package com.example.sytemonpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ParkingSlotAdapter extends BaseAdapter {

    private Context context;
    private List<ParkingSlot> slotList;
    private LayoutInflater inflater;

    public ParkingSlotAdapter(Context context, List<ParkingSlot> slotList) {
        this.context = context;
        this.slotList = slotList;
        this.inflater = LayoutInflater.from(context);
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
        ImageView imgSlot;
        TextView tvSlotName;
        TextView tvStatus;
        TextView tvfees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
            holder = new ViewHolder();
            holder.imgSlot = convertView.findViewById(R.id.img_slot);
            holder.tvSlotName = convertView.findViewById(R.id.tv_slot_name);
            holder.tvStatus = convertView.findViewById(R.id.tv_Status);
            holder.tvfees = convertView.findViewById(R.id.tv_fees);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParkingSlot slot = slotList.get(position);
        holder.tvSlotName.setText(slot.getName());
        holder.tvfees.setText("Rate Per Hour : " + slot.getFees());
        holder.tvStatus.setText("Checking...");
        holder.tvStatus.setTextColor(Color.GRAY);

        // If you use a library like Glide or Picasso to load images.
        // Example using Glide:
        // Glide.with(context)
        //        .load(slot.getImageUrl())
        //        .placeholder(R.drawable.images)
        //        .into(holder.imgSlot);

        // Fetch availability asynchronously
        checkAvaibility checker = new checkAvaibility(slot.getSlotId(), context, availability -> {
            holder.tvStatus.setText(availability);
            if ("Available".equalsIgnoreCase(availability.trim())) {
                holder.tvStatus.setTextColor(Color.GREEN);
            } else if ("Occupied".equalsIgnoreCase(availability.trim())) {
                holder.tvStatus.setTextColor(Color.RED);
            } else {
                holder.tvStatus.setTextColor(Color.DKGRAY);
            }
        });
        checker.fetchAvailability();

        convertView.setOnClickListener(v -> {
            Toast.makeText(context, "Slot clicked: " + slot.getName() + slot.getSlotId(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, User_Slot_Details.class);
            intent.putExtra("Slot Name", slot.getName());
            intent.putExtra("Slot Fees", slot.getFees());
            intent.putExtra("Slot Id", slot.getSlotId());
            context.startActivity(intent);
        });

        return convertView;
    }
}
