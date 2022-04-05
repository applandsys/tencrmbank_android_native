package com.example.a10crmbank;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static android.R.layout.simple_dropdown_item_1line;
import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_item;

public class IdVipActivity extends AppCompatActivity {

    Spinner spinner;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_id);

        spinner = findViewById(R.id.packagespinner);
        button = findViewById(R.id.submit);

     //  ArrayList<Dropdown> packages = new ArrayList<Dropdown>();
        ArrayList<String> names = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.VIPPACKAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i=0; i< arr.length();i++){
                                JSONObject obj = arr.getJSONObject(i);
                               /*
                                Dropdown dropdown = new Dropdown(
                                        obj.getString("name")
                                );
                                */

                                names.add(obj.getString("name"));
                                    Log.d("fuck",obj.getString("name"));
                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.vip_package,R.id
                                    .package_item,names);
                            spinner.setAdapter(adapter);
                            /*
                             //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            for(int i=0;i<obj.length();i++) {
                                Dropdown dropdown = new Dropdown(
                                        obj.getString("id"),
                                        obj.getString("name")
                                );
                                Log.d("Fuck", obj.getString("name"));
                                packages.add(dropdown);
                            }

                           // Log.i("fuck",obj.getString("name"));
                                */

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
                params.put("action", "login");
                params.put("version", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


       button.setOnClickListener(view -> {
           String selected_item =  spinner.getSelectedItem().toString();
         //  Log.d("fuck",selected_item);
           Intent intent =  new Intent(getApplicationContext(), PaymentMethodActivity.class);
           intent.putExtra("selected_package",selected_item);
           startActivity(intent);
       });

    }
}