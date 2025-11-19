package com.example.sytemonpark;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class paymentDialog {
    public void showPaymentDialog(Context context, String otp) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.user_paymentdialog);
        dialog.setCancelable(false);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }

        TextView tvTitle = dialog.findViewById(R.id.tv_payment_done);
        TextView tvOtpLabel = dialog.findViewById(R.id.tv_otp_label);
        TextView tvOtpValue = dialog.findViewById(R.id.tv_otp_value);
        Button btnDone = dialog.findViewById(R.id.btn_done);

        tvTitle.setText("PAYMENT DONE");
        tvOtpLabel.setText("Your OTP is:");
        tvOtpValue.setText(otp);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,User_Home_Page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });

        dialog.show();
    }


}
