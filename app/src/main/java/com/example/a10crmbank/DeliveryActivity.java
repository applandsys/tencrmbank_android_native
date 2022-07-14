package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity {


    String  login_id , user_id;
    TextView product_info;
    String request_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_activity);
        product_info =  findViewById(R.id.product_info);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        user_id = users.getUser_id();
        login_id = users.getLoginid();

        if(user_id==null){
            user_id= "0";
        }

        Intent intent = getIntent();
        String transaction_type = intent.getStringExtra("transaction_type");
        String trxid = intent.getStringExtra("trxid");

        if(transaction_type.matches("freefire_uc")){
            request_url = URLs.SEARCH_FREEFIRE;
        }else if(transaction_type.matches("dollar_card") || transaction_type.matches("rupi_card") ){
            request_url = URLs.SEARCH_CARD;
        }


            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, request_url,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String title = jsonObject.getString("title");
                            String message_text = jsonObject.getString("message");

                            product_info.setText(message_text);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.d("fuck",error.toString());
                    }){
                        protected Map<String,String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id",user_id);
                            params.put("transaction_type",transaction_type);
                            params.put("trxid",trxid);
                            return params;
                        }
                    };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequestConfirm);



        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }
}