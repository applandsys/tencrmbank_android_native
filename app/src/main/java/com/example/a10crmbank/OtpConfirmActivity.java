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


            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, URLs.CARDBUY_CONFIRM,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String title = jsonObject.getString("title");
                            String message_text = jsonObject.getString("message");

                            if(status.matches("success") || status.matches("failed")){
                                // alert dialog
                                final AlertDialog.Builder builder = new AlertDialog.Builder(OtpConfirmActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(OtpConfirmActivity.this).inflate(R.layout.customview, viewGroup, false);
                                Button buttonOk=dialogView.findViewById(R.id.buttonOk);

                                TextView alert_title = dialogView.findViewById(R.id.alert_title);
                                TextView alert_description = dialogView.findViewById(R.id.alert_description);
                                alert_title.setText(title);
                                alert_description.setText(message_text);
                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();

                                        if(user_id!="0"){
                                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        }else{
                                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                        }

                                    }
                                });
                                // end alert

                            }

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
                            params.put("verify_code",otp_code);
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