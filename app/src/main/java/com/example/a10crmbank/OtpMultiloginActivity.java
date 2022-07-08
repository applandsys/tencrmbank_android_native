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

public class OtpMultiloginActivity extends AppCompatActivity {

    EditText otp_edittext;
    TextView note_email;
    String   otp_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_multilogin);
        otp_edittext = findViewById(R.id.otp_edittext);

        note_email =  findViewById(R.id.note_email);

           Intent intent = getIntent();
           String email_id = intent.getStringExtra("email_id");
           String mytext= email_id + " ইমেইলে যে গিয়েছে তা এখানে লিখুন বা কপি পেস্ট করুন";

           note_email.setText(mytext);


        findViewById(R.id.confirm_button).setOnClickListener((View v) -> {

            otp_code = otp_edittext.getText().toString();
            if(TextUtils.isEmpty(otp_code)){
                otp_edittext.setError("Please Input Otp");
                otp_edittext.requestFocus();
                return;
            }

            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, URLs.LOGIN_OTP_VERIFY,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");
                            String message_text = obj.getString("message");

                            if(status.matches("success")){
                                Users userss = new Users(
                                        obj.getString("user_id"),
                                        obj.getString("playerid"),
                                        obj.getString("name"),
                                        obj.getString("loginid")
                                );

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(userss);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }else if(status.matches("expired")){
                                openAlert("Login Failed",message_text);
                                return;
                            }else if(status.matches("unseccess")){
                                openAlert("Login Failed",message_text);
                                return;
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

    private void openAlert(String title, String description){  // alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(OtpMultiloginActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(OtpMultiloginActivity.this).inflate(R.layout.customview, viewGroup, false);
        Button buttonOk=dialogView.findViewById(R.id.buttonOk);

        TextView alert_title = dialogView.findViewById(R.id.alert_title);
        TextView alert_description = dialogView.findViewById(R.id.alert_description);
        alert_title.setText(title);
        alert_description.setText(description);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        // end alert
    }



}