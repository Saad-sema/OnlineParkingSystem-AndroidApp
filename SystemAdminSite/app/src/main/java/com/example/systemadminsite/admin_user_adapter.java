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

public class admin_user_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<admin_user_data> userList;
    private ManageApis changeStatus;
    public boolean res;

    public admin_user_adapter(Context context, ArrayList<admin_user_data> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.admin_user_manage_layout, parent, false);
        }

        admin_user_data user = userList.get(position);

        TextView tvUserId = convertView.findViewById(R.id.tvUserId);
        TextView tvUserName = convertView.findViewById(R.id.tvUserId);
        TextView tvUserEmail = convertView.findViewById(R.id.tvUserEmail);
        TextView tvPassword= convertView.findViewById(R.id.tvPassword);
        TextView tvPhoneNo = convertView.findViewById(R.id.tvAmount);
        TextView tvStatus = convertView.findViewById(R.id.tvHistory);
        TextView tvRegistration = convertView.findViewById(R.id.tvRegistrationDate);
        Button btnRemove = convertView.findViewById(R.id.btnRemove);

        tvUserId.setText(user.getUserId());
        tvUserName.setText(user.getUserName());
        tvUserEmail.setText(user.getEmail());
        tvPassword.setText(user.getUserPassword());
        tvStatus.setText(user.getStatus());
        tvPhoneNo.setText(user.getPhoneNo());
        tvRegistration.setText(user.getUserRegistrationdate());
        if(user.getStatus().equalsIgnoreCase("Blocked")) {
            btnRemove.setText("Unblock");
            btnRemove.setOnClickListener(v -> {
                    showDoneDialog(user.getUserId(), "Unblock");
            });
        }else{
            btnRemove.setText("Block");
            btnRemove.setOnClickListener(v -> {
                    showDoneDialog(user.getUserId(),"Block");
            });
        }

        return convertView;
    }
    private void showDoneDialog(String slotId , String status) {
        new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to "+status)
                .setPositiveButton("Yes", (dialog, which) -> {

                    changeStatus = new ManageApis();
                    if(status.equalsIgnoreCase("Unblock")){
                        changeStatus.changeStatus(context, slotId,"Active");
                    }else {
                        changeStatus.changeStatus(context, slotId, "Blocked");
                    }

                    Toast.makeText(context, "Slot Status Changed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {

                    dialog.dismiss();
                })
                .setCancelable(false) // prevent dismiss by tapping outside or back button
                .show();
    }
}

