package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);

        player_id_edittext = findViewById(R.id.player_id_edittext);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);


        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        findViewById(R.id.gift).setOnClickListener(view -> {
            player_id = player_id_edittext.getText().toString().trim();
            chips_amount = chips_amount_edittext.getText().toString().trim();
            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Please enter player id");
                player_id_edittext.requestFocus();
            }
            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Pleasse enter amount");
                chips_amount_edittext.requestFocus();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GIFT_TPG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String> params = new HashMap<>();
                   params.put("action","gift_tpg");
                   params.put("userid",user_id);
                   params.put("request","1");
                   params.put("chips",chips_amount);
                   params.put("playerid",player_id);
                   params.put("loginid",login_id);
                   return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        });



        findViewById(R.id.back_imagevie).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });

    }
}