package com.example.systemmanagersite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class view_ConfResAdapter extends BaseAdapter {
    Context context;
    List<view_ConfRes_data> bookingList;
    public view_ConfResAdapter(Context context, List<view_ConfRes_data> bookingList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.confirmres_layout, parent, false);
        }
        view_ConfRes_data booking = bookingList.get(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvSlotName = convertView.findViewById(R.id.tvSlotName);
        TextView tvVehicleNo = convertView.findViewById(R.id.tvVehicleNo);
        TextView tvEntryTime = convertView.findViewById(R.id.tvEntryTime);
        TextView tvExitTime = convertView.findViewById(R.id.tvExitTime);
        TextView tvPhoneNo = convertView.findViewById(R.id.tvPhoneNo);
        TextView tvPaymentStatus = convertView.findViewById(R.id.tvPaymentStatus);
        Button  btnConfirm = convertView.findViewById(R.id.btnExtend);
        tvUserName.setText(booking.getUserName());
        tvSlotName.setText(booking.getSlotName());
        tvVehicleNo.setText(booking.getVehicleNo());
        tvEntryTime.setText(booking.getEntryTime());
        tvExitTime.setText(booking.getExitTime());
        tvPhoneNo.setText(booking.getPhoneNo());
        tvPaymentStatus.setText(booking.getPaymentStatus());
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOTP checkotp = new checkOTP(context,booking.getOTP(),booking.getBookingId(),"give_Confirmation");
            }
        });

        return convertView;
    }
}
