package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name_edittext,mobile_number_editext,email_edittext,password_edittext,againpassword_edittext;
    private String name, mobile_number, email, password, againpassword;
    SharedPreferences sharedPref;
    private final String SHARED_PREF_NAME = "mypref";
    private final String KEY_PLAYERID = "playerid";
    private final String KEY_USERID = "user_id";
    private final String KEY_NAME = "name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        name_edittext = findViewById(R.id.name_edittext);
        mobile_number_editext = findViewById(R.id.mobile_number_editext);
        email_edittext =  findViewById(R.id.email_edittext);
        password_edittext = findViewById(R.id.password_edittext);
        againpassword_edittext = findViewById(R.id.againpassword_edittext);

        sharedPref = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE) ;
        SharedPreferences.Editor editor = sharedPref.edit();

        findViewById(R.id.btn_register).setOnClickListener(view -> {

            name = name_edittext.getText().toString();
            mobile_number =  mobile_number_editext.getText().toString().trim();
            email = email_edittext.getText().toString().trim();
            password = password_edittext.getText().toString().trim();
            againpassword = againpassword_edittext.getText().toString().trim();

            if(TextUtils.isEmpty(name)){
                name_edittext.setError("Please Enter Name");
                name_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(mobile_number)){
                mobile_number_editext.setError("Please Enter Mobile Number");
                mobile_number_editext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(email)){
                email_edittext.setError("Please enter Email");
                email_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(password)){
                password_edittext.setError("Please enter password");
                password_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(againpassword)){
                againpassword_edittext.setError("Please confirm password");
                againpassword_edittext.requestFocus();
                return;
            }



            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                String message = obj.getString("message");


                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                                if( obj.getBoolean("status")==true || obj.getString("signup")=="success" ){

                                    Users users = new Users(
                                            obj.getString("user_id"),
                                            obj.getString("playerid"),
                                            obj.getString("name"),
                                            obj.getString("loginid")
                                    );

                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(users);


                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
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
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "register");
                    params.put("version", "1");
                    params.put("name", name);
                    params.put("mobile_number", mobile_number);
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

           // startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });


    }
}