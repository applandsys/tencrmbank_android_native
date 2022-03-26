package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        ImageView setting_back_imagevie= findViewById(R.id.setting_back_imagevie);

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


                            name_textview.setText(myobj.getString("name") );
                            account_type.setText(myobj.getString("account_type") );
                            mbankid_textview.setText(myobj.getString("company_id") );
                            player_id_textview.setText(myobj.getString("playerid") );
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
                params.put("userid", user_id);
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

        findViewById(R.id.player_id_textview).setOnClickListener(view -> {
            Toast.makeText(this,"Please set your player id",Toast.LENGTH_LONG).show();
        });

        setting_back_imagevie.setOnClickListener(v -> {
            finish();
        });

    }
}