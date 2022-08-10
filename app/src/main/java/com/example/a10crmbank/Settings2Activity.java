package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Settings2Activity extends AppCompatActivity {

    EditText district_edittext,thana_edittext,postoffice_edittext,house_edittext,postcode_edittext;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_2);

        district_edittext = findViewById(R.id.district_edittext);
        thana_edittext = findViewById(R.id.thana_edittext);
        postoffice_edittext = findViewById(R.id.postoffice_edittext);
        postcode_edittext = findViewById(R.id.postcode_edittext);
        house_edittext = findViewById(R.id.house_edittext);
        submit = findViewById(R.id.submit);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();
        // Get Home page data from server
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ADDRESS_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fuck",response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            String myDistrict = obj.getString("district");
                            String myThana = obj.getString("thana");
                            String myPostoffice = obj.getString("postoffice");
                            String myPostcode = obj.getString("postcode");
                            String myHouse = obj.getString("house");
                            district_edittext.setText(myDistrict, TextView.BufferType.EDITABLE);
                            thana_edittext.setText(myThana, TextView.BufferType.EDITABLE);
                            postoffice_edittext.setText(myPostoffice, TextView.BufferType.EDITABLE);
                            postcode_edittext.setText(myPostcode, TextView.BufferType.EDITABLE);
                            house_edittext.setText(myHouse, TextView.BufferType.EDITABLE);
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
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "address_info");
                params.put("user_id", user_id);
                params.put("login_id", login_id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

        submit.setOnClickListener(view -> {
            String district = district_edittext.getText().toString();
            String thana = thana_edittext.getText().toString();
            String postoffice = postoffice_edittext.getText().toString();
            String postcode = postcode_edittext.getText().toString();
            String house = house_edittext.getText().toString();

            if(TextUtils.isEmpty(district)){
                district_edittext.setError("Input Discrict");
                district_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(thana)){
                thana_edittext.setError("Input Thana");
                thana_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(postcode)){
                postcode_edittext.setError("Input Post Code");
                postcode_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(postoffice)){
                postoffice_edittext.setError("Input Post Office");
                postoffice_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(house)){
                house_edittext.setError("Input House and Road");
                house_edittext.requestFocus();
                return;
            }

            StringRequest stringrequest  = new StringRequest(Request.Method.POST,URLs.UPDATE_ADDRESS,
                    response -> {
                Log.d("fuck",response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Settings2Activity.this,"Address Updated",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Settings2Activity.this,SettingsActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },error -> {
                         Log.d("fuck",error.toString());
                    }   
            ){
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params= new HashMap<>();
                    params.put("user_id",user_id);
                    params.put("login_id",login_id);
                    params.put("district",district);
                    params.put("thana",thana);
                    params.put("postcode",postcode);
                    params.put("postoffice",postoffice);
                    params.put("house",house);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringrequest);


        });

    }



}