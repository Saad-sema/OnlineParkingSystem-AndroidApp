package com.example.systemadminsite;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class admin_slot_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<admin_slot_data> slotList;
    private ManageApis removeSlot;
    public boolean res;

    public admin_slot_adapter(Context context, ArrayList<admin_slot_data> slotList) {
        this.context = context;
        this.slotList = slotList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_slot_manage_layout, parent, false);
        }

        admin_slot_data slot = slotList.get(position);

        TextView tvSlotId = convertView.findViewById(R.id.tvSlotId);
        TextView tvSlotName = convertView.findViewById(R.id.tvPaymentTime);
        TextView tvSlotLocation = convertView.findViewById(R.id.tvSlotLocation);
        TextView tvRate = convertView.findViewById(R.id.tvRate);
        TextView tvExtraCharge = convertView.findViewById(R.id.tvExtraRate);
        Button btnRemove = convertView.findViewById(R.id.btnRemove);

        tvSlotId.setText(slot.getSlotId());
        tvSlotName.setText(slot.getSlotName());
        tvSlotLocation.setText(slot.getSlotLocation());
        tvRate.setText(slot.getRate());
        tvExtraCharge.setText(slot.getExtraCharge());


        btnRemove.setOnClickListener(v -> {
            showDoneDialog(slot.getSlotId());
        });
        return convertView;
    }
    private void showDoneDialog(String slotId ) {
        new AlertDialog.Builder(context)

                .setMessage("Are you sure you want to remove?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    removeSlot = new ManageApis();
                    removeSlot.removeSlot(context, slotId);
                    res=true;
                    Toast.makeText(context, "Slot removed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Handle Cancel action here, e.g., just close dialog
                    res=false;
                    dialog.dismiss();
                })
                .setCancelable(false) // prevent dismiss by tapping outside or back button
                .show();
    }
}

