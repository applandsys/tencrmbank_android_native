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

public class RupeCardActivity extends AppCompatActivity {
    Spinner spinner;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rupee_card);

        spinner = findViewById(R.id.packagespinner);
        button = findViewById(R.id.submit);
        ArrayList<String> names = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.RUPICARD_PACKAGE,
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
                params.put("request_type", "rupi_card_package");
                params.put("version", "2");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        button.setOnClickListener(view -> {
            Integer ide =spinner.getSelectedItemPosition();
            String selected_item =  ide.toString();

            Intent intent =  new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type","rupi_card");
            intent.putExtra("selected_package",selected_item);
            startActivity(intent);
        });
    }
}