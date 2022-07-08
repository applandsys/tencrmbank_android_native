package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetNameActivity extends AppCompatActivity {
    Button submit;
    EditText name_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_name);
        name_edittext = findViewById(R.id.name_edittext);
        submit = findViewById(R.id.submit);
        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        submit.setOnClickListener(view ->{
            String myname = name_edittext.getText().toString().trim();
            if (TextUtils.isEmpty(myname)) {
                name_edittext.setError("Please enter your Name");
                name_edittext.requestFocus();
                return;
            }

            StringRequest loginstringRequest = new StringRequest(Request.Method.POST,URLs.UPDATE_MYNAME,
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
                    params.put("action", "update_myname");
                    params.put("version", "2");
                    params.put("myname", myname);
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