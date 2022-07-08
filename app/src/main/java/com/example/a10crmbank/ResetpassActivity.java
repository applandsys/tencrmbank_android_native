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

public class ResetpassActivity extends AppCompatActivity {

    EditText player_edittext ;
    String player_id;
    Button confirm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpass);
        player_edittext = findViewById(R.id.player_edittext);
        confirm_button = findViewById(R.id.confirm_button);


        confirm_button.setOnClickListener((View v) -> {

            confirm_button.setEnabled(false);
            confirm_button.setClickable(false);

            player_id = player_edittext.getText().toString();
            if(TextUtils.isEmpty(player_id)){
                player_edittext.setError("Please input PlayerId or Phone number");
                player_edittext.requestFocus();
                return;
            }

            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, URLs.RESET_PASS,
                    response -> {
                            Log.d("fuck",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message_text = jsonObject.getString("message");
                            String email_id = jsonObject.getString("email_id");

                            if(status.matches("success")){

                                Intent intent =  new Intent(getApplicationContext(), OtpResetpassActivity.class);
                                intent.putExtra("email_id",email_id);
                                startActivity(intent);

                            }else if(status.matches("unseccess")){

                                confirm_button.setEnabled(true);
                                confirm_button.setClickable(true);

                                Log.d("fuck","Unscuess hoise");
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ResetpassActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(ResetpassActivity.this).inflate(R.layout.customview, viewGroup, false);
                                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                                TextView alert_title = dialogView.findViewById(R.id.alert_title);
                                TextView alert_description = dialogView.findViewById(R.id.alert_description);
                                alert_title.setText("Password Recovery Failed");
                                alert_description.setText(message_text);
                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                return;

                            }else if(status.matches("notfound")){

                                confirm_button.setEnabled(true);
                                confirm_button.setClickable(true);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(ResetpassActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(ResetpassActivity.this).inflate(R.layout.customview, viewGroup, false);
                                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                                TextView alert_title = dialogView.findViewById(R.id.alert_title);
                                TextView alert_description = dialogView.findViewById(R.id.alert_description);
                                alert_title.setText("Password Recovery Failed");
                                alert_description.setText(message_text);
                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                return;

                            }

                        } catch (JSONException e) {
                            Log.d("fuck","catch koro"+e.toString());
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.d("fuck","hima sma"+error.toString());
                    }){
                        protected Map<String,String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("player_id",player_id);
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