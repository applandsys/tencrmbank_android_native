package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PubgActivity extends AppCompatActivity {
    Spinner spinner;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pubg);

        spinner = findViewById(R.id.packagespinner);
        button = findViewById(R.id.submit);
        ArrayList<String> names = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.PUBG_PACKAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i=0; i< arr.length();i++){
                                JSONObject obj = arr.getJSONObject(i);
                                names.add(obj.getString("name"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.package_list,R.id
                                    .package_item,names);
                            spinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request_type", "pubg_package");
                params.put("version", "2");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        findViewById(R.id.submit).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            startActivity(intent);
        });
    }
}