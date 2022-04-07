package com.example.a10crmbank;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class GullakBreakActivity extends AppCompatActivity {

    Spinner spinner;
    Button button;
    EditText player_id_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gullak_break);
        spinner = findViewById(R.id.packagespinner);
        button = findViewById(R.id.submit);

        player_id_edittext = findViewById(R.id.player_id_edittext);

        ArrayList<String> names = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GULLACK_PACKAGE,
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
                params.put("action", "login");
                params.put("version", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        button.setOnClickListener(view -> {
            String player_id = player_id_edittext.getText().toString();
            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Put player Id");
                player_id_edittext.requestFocus();
                return;
            }

            Integer ide =spinner.getSelectedItemPosition();
            String selected_item =  ide.toString();


            Intent intent =  new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type","gullak_break");
            intent.putExtra("player_id",player_id);
            intent.putExtra("selected_package",selected_item);
            startActivity(intent);
        });


        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }
}