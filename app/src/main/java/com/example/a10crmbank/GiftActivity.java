package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiftActivity extends AppCompatActivity {

    EditText player_id_edittext, chips_amount_edittext;
    String player_id, chips_amount;
    String post_login_id, post_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);

        player_id_edittext = findViewById(R.id.player_id_edittext);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);



      //  chips_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "50")});

        player_id_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(player_id_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("প্লেয়ার আইডি", "প্লেয়ার আইডি ও বুলেট কোড এর ফরমেট দিয়ে দিন (প্লেয়ার আইডি গেমসের সেটিংসে পাবেন)");
                return true;
            }
        } );

        chips_amount_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(chips_amount_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("1-50CR ", "এক সাথে 1-50CR  গিফ্ট করা যাবে");
                return true;
            }
        } );


        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        if(login_id == null || login_id.isEmpty()){
            post_login_id="0";
        }

        if(user_id == null || user_id.isEmpty()){
            post_user_id="0";
        }

        findViewById(R.id.gift).setOnClickListener(view -> {
            player_id = player_id_edittext.getText().toString().trim();
            chips_amount = chips_amount_edittext.getText().toString().trim();
            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Please enter player id");
                player_id_edittext.requestFocus();
                return;
            }

            String regexString = "[A-Za-z]{4}[0-9]{3}|100+[0-9]{5,12}";

            if(!player_id.trim().matches(regexString))
            {
                player_id_edittext.setError("Please enter correct format");
                player_id_edittext.requestFocus();
                return;
            }


            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Pleasse enter amount");
                chips_amount_edittext.requestFocus();
                return;
            }

            if(Integer.parseInt(chips_amount)>=50){
                chips_amount_edittext.setError("Enter Amount 1-50");
                chips_amount_edittext.requestFocus();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GIFT_TPG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("fuck",response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                String status = obj.getString("status");
                                String message = obj.getString("message");
                                Log.d("fuck",status);
                                if(status.matches("pushed") ){
                                    showInfo("Paused","Account is paused");
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("fuck",error.toString());
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> params = new HashMap<>();
                   params.put("transaction_type","gift_tpg");
                    params.put("userid", user_id);
                    params.put("loginid", login_id);
                   params.put("request","1");
                   params.put("chips",chips_amount);
                   params.put("playerid",player_id);
                   return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });
    }

    private void showInfo(String title, String description){
        final AlertDialog.Builder builder = new AlertDialog.Builder(GiftActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(GiftActivity.this).inflate(R.layout.customview, viewGroup, false);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
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
    }

}