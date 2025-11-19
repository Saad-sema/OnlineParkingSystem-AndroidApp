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

public class admin_payment_adapter extends BaseAdapter {
    Context context;
    List<admin_payment_data> paymentList;
    ManageApis api;
    public admin_payment_adapter(Context context, List<admin_payment_data> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
    }
    @Override
    public int getCount() {
        return paymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_payment_maange_layout, parent, false);
        }
        admin_payment_data payment = paymentList.get(position);
        Button btnRemove=convertView.findViewById(R.id.btnRemove);
        TextView tvPaymentTime = convertView.findViewById(R.id.tvPaymentTime);
        TextView tvPaymentMethod = convertView.findViewById(R.id.tvPaymentMethod);
        TextView tvExtraCharge = convertView.findViewById(R.id.tvExtraCharge);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);
        TextView tvPaymentStatus = convertView.findViewById(R.id.tvStatus);
        TextView tvBookingId=convertView.findViewById(R.id.tvBookingId);
        TextView tvPaymentID =convertView.findViewById(R.id.tvPayemntId);
        TextView tvUserId =convertView.findViewById(R.id.tvUserId);
        tvBookingId.setText(payment.getBookingId());
        tvPaymentID.setText(payment.getPaymentId());
        tvPaymentTime.setText(payment.getPaymentTime());
        tvPaymentMethod.setText(payment.getPaymentMethod());
        tvExtraCharge.setText(payment.getExtraCharge());
        tvAmount.setText(payment.getAmount());
        tvUserId.setText(payment.getUserId());
        tvPaymentStatus.setText(payment.getStatus());
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelOrDoneDialog(payment.getPaymentId());
            }
        });

        return convertView;
    }
    private void showCancelOrDoneDialog(String paymentId){
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to remove?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    api = new ManageApis();
                    api.removePayment(context,paymentId);
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
