package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetPlayeridActivity extends AppCompatActivity {
    Button submit;
    EditText player_id_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_playerid);
        player_id_edittext = findViewById(R.id.player_id_edittext);
        submit = findViewById(R.id.submit);
        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        submit.setOnClickListener(view ->{
            String player_id = player_id_edittext.getText().toString().trim();
            if (TextUtils.isEmpty(player_id)) {
                player_id_edittext.setError("Please enter your Player Id");
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

            StringRequest loginstringRequest = new StringRequest(Request.Method.POST,URLs.UPDATE_PLAYER_ID,
                    response -> {
                        Log.d("fuck",response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("fuck","error ase cactch");
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    },error -> {
                        Log.d("fuck",error.toString());
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "update_playerid");
                    params.put("version", "2");
                    params.put("player_id", player_id);
                    params.put("user_id", user_id);
                    params.put("login_id", login_id);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(loginstringRequest);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });



    }
}