package com.example.systemadminsite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class admin_history_adapter extends BaseAdapter {
    Context context;
    List<admin_history_data> bookingList;
    ManageApis api;
    public admin_history_adapter(Context context, List<admin_history_data> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }
    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_history_manage_layout, parent, false);
        }
        admin_history_data booking = bookingList.get(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserId);
        TextView tvSlotName = convertView.findViewById(R.id.tvPaymentTime);
        TextView tvVehicleNo = convertView.findViewById(R.id.tvPaymentMethod);
        TextView tvEntryTime = convertView.findViewById(R.id.tvHistory);
        TextView tvExitTime = convertView.findViewById(R.id.tvExtraCharge);
        TextView tvPhoneNo = convertView.findViewById(R.id.tvAmount);
        TextView tvPaymentStatus = convertView.findViewById(R.id.tvHistory);
        Button btnConfirm = convertView.findViewById(R.id.btnRemove);
        TextView tvBookingId=convertView.findViewById(R.id.tvBookingId);
        TextView tvPaymentID =convertView.findViewById(R.id.tvPayemntId);
        tvBookingId.setText(booking.getBookingId());
        tvPaymentID.setText(booking.getPaymentId());
        tvUserName.setText(booking.getUserName());
        tvSlotName.setText(booking.getSlotName());
        tvVehicleNo.setText(booking.getVehicleNo());
        tvEntryTime.setText(booking.getEntryTime());
        tvExitTime.setText(booking.getExitTime());
        tvPhoneNo.setText(booking.getPhoneNo());
        tvPaymentStatus.setText(booking.getPaidAmount());
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelOrDoneDialog(booking.getHistoryId());
            }
        });

        return convertView;
    }
    private void showCancelOrDoneDialog(String historyId){
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to remove?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    api = new ManageApis();
                    api.removeHistory(context,historyId);
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Handle Cancel action here, e.g., just close dialog
                    dialog.dismiss();
                })
                .setCancelable(false) // prevent dismiss by tapping outside or back button
                .show();
    }
}
