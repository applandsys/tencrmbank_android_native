package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    TextView name_textview, account_type, mbankid_textview,player_id_textview,mobile_number_textview,email_textview;
    String name_text,player_id_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        name_textview = findViewById(R.id.name_textview);
        account_type = findViewById(R.id.account_type);
        mbankid_textview = findViewById(R.id.mbankid_textview);
        player_id_textview = findViewById(R.id.player_id_textview);
        mobile_number_textview = findViewById(R.id.mobile_number_textview);
        email_textview = findViewById(R.id.email_textview);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();
        // Get Home page data from server
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCOUNT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            JSONArray userJson = obj.getJSONArray("playerdata");
                            String rawdata = userJson.getString(0);
                            JSONObject myobj = new JSONObject(rawdata);

                            name_text = myobj.getString("name");
                            player_id_text = myobj.getString("playerid");

                            if(player_id_text==null || player_id_text==""){

                                findViewById(R.id.player_id_textview).setOnClickListener(view -> {
                                    startActivity(new Intent(getApplicationContext(), SetPlayeridActivity.class));
                                });
                            }

                            if(name_text.equals(null)||name_text.equals("")){
                                findViewById(R.id.name_textview).setOnClickListener(view -> {
                                    startActivity(new Intent(getApplicationContext(), SetNameActivity.class));
                                });
                            }

                            name_textview.setText(name_text);
                            account_type.setText(myobj.getString("player_type") );
                            mbankid_textview.setText(myobj.getString("company_id") );
                            player_id_textview.setText(player_id_text);
                            mobile_number_textview.setText(myobj.getString("phone") );
                            email_textview.setText(myobj.getString("email") );

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "account_info");
                params.put("user_id", user_id);
                params.put("login_id", login_id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        // Get Home Page data fromm server

        findViewById(R.id.address).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Settings2Activity.class));
        });
        findViewById(R.id.change_pass).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
        });


        findViewById(R.id.back_imageview).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });

        findViewById(R.id.logout_button).setOnClickListener(v -> {

            // Server e logout request send korte hobe
            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URLs.LOGOUT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<>();
                    params.put("action","logout");
                    params.put("user_id",user_id);
                    params.put("login_id", login_id);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest1);
            // Shared Preference Remove korte hobe
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });


        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });


    }
}