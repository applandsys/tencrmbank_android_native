package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpConfirmActivity extends AppCompatActivity {

    EditText otp_edittext ;
    String  login_id , user_id, otp_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_confirm);
        otp_edittext = findViewById(R.id.otp_edittext);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        user_id = users.getUser_id();
        login_id = users.getLoginid();

        if(user_id==null){
            user_id= "0";
        }

        Intent intent = getIntent();

        findViewById(R.id.confirm_button).setOnClickListener((View v) -> {

            otp_code = otp_edittext.getText().toString();
            if(TextUtils.isEmpty(otp_code)){
                otp_edittext.setError("Plese input Otp");
                otp_edittext.requestFocus();
                return;
            }


            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, URLs.OTP_CONRIFM,
                    response -> {
                        Log.d("fuck",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message_text = jsonObject.getString("message");
                            Log.d("fuck",message_text);
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
                            params.put("otp_code",otp_code);
                            return params;
                        }
                    };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequestConfirm);

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }
}