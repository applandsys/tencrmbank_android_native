package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText newpass_edittext,confirmpass_edittext;
    String newpass,confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        newpass_edittext = findViewById(R.id.newpass_edittext);
        confirmpass_edittext = findViewById(R.id.confirmpass_edittext);


        findViewById(R.id.change_pass_button).setOnClickListener(v ->  {

            newpass = newpass_edittext.getText().toString();
            confirmpass =confirmpass_edittext.getText().toString();

            if(TextUtils.isEmpty(newpass)){
                newpass_edittext.setError("Please enter a new Password");
                newpass_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(confirmpass)){
                confirmpass_edittext.setError("Please confirm Password");
                confirmpass_edittext.requestFocus();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGEPASS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject obj = new JSONObject(response);
                                Toast.makeText(getApplicationContext(),"Passwrod Change Successfully",Toast.LENGTH_LONG).show();
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
                protected Map<String,String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<>();
                    params.put("action", "login");
                    params.put("version", "1");
                    params.put("new_password",newpass);
                    params.put("confirm_password",confirmpass);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        });


        findViewById(R.id.back_imageview).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });


    }
}