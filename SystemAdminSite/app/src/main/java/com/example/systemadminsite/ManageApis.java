package com.example.systemadminsite;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManageApis {
    public void addSlot(Context context,String slotName,String slotLocation,String rate,String extraCharge){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "addSlot");
            params.put("slotName",slotName);
            params.put("slotLocation",slotLocation);
            params.put("rate",rate);
            params.put("extraCharge",extraCharge);
            return params;
        }
        };
        queue.add(request);
    }
    public void removeSlot(Context context,String slotId){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "removeSlot");
            params.put("slotId",slotId);
            return params;
        }
        };
        queue.add(request);
    }
    public void updateRateSlot(Context context,String slotId,String newRate){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "changeCharges");
            params.put("slotId",slotId);
            params.put("newRate",newRate);
            return params;
        }
        };
        queue.add(request);
    }
    public void updateExtraRateSlot(Context context,String slotId,String newExtraRate){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "changeExtraCharges");
            params.put("slotId",slotId);
            params.put("newExtraCharge",newExtraRate);
            return params;
        }
        };
        queue.add(request);
    }
    public void addManager(Context context,String mangerName,String email,String password,String phoneNo){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "addManager");
            params.put("managerName",mangerName);
            params.put("email",email);
            params.put("password",password);
            params.put("phoneNo",phoneNo);
            return params;
        }
        };
        queue.add(request);
    }
    public void changeStatus(Context context,String userId,String status){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "changeUserStatus");
            params.put("userId",userId);
            params.put("status",status);
            return params;
        }
        };
        queue.add(request);
    }
    public void removeBooking(Context context,String bookingId){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "removeBooking");
            params.put("bookingId",bookingId);
            return params;
        }
        };
        queue.add(request);
    }
    public void removeHistory(Context context,String historyId){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "removeHistory");
            params.put("historyId",historyId);
            return params;
        }
        };
        queue.add(request);
    }
    public void removePayment(Context context,String paymentId){
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/admin_task.php";

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request= new StringRequest(Request.Method.POST, url,
                response -> {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String message = obj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        )
        {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("action", "removePayment");
            params.put("paymentId",paymentId);
            return params;
        }
        };
        queue.add(request);
    }
    public interface VolleyCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public void deshboardRec(Context context, String action, VolleyCallback callback) {
        String base_url=context.getString(R.string.url);
        String url = base_url+"/parking_system/dashboardRec.php";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String result = obj.getString("result");
                            callback.onSuccess(result);
                        }
                    } catch (JSONException e) {
                        callback.onError(e.getMessage());
                    }
                },
                error -> callback.onError(error.getMessage())
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", action);
                return params;
            }
        };

        queue.add(request);
    }

}
