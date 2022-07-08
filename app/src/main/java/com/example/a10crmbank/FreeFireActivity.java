package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

public class FreeFireActivity extends AppCompatActivity {

    Spinner spinner;
    Button submit, search;
    EditText trxid_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_fire);
        submit = findViewById(R.id.submit);
        search = findViewById(R.id.search);
        trxid_edittext =  findViewById(R.id.trxid_edittext);
// start spinner
        spinner = findViewById(R.id.packagespinner);
        ArrayList<String> names = new ArrayList<String>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FREEFIRE_PACKAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray arr = new JSONArray(response);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                names.add(obj.getString("name"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.package_list, R.id
                                    .package_item, names);
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("request_type", "freefire_package");
                params.put("version", "2");
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
// end spinner//
        submit.setOnClickListener(view -> {
            Integer ide = spinner.getSelectedItemPosition();
            String selected_item = ide.toString();

            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type", "freefire_uc");
            intent.putExtra("selected_package", selected_item);
            startActivity(intent);
        });

        search.setOnClickListener(view -> {
            String trxid = trxid_edittext.getText().toString();
            if(TextUtils.isEmpty(trxid)){
                trxid_edittext.setError("Enter TrxId");
                trxid_edittext.requestFocus();
                return;
            }

            Intent intent1 =  new Intent(getApplicationContext(),DeliveryActivity.class);
            intent1.putExtra("transaction_type", "freefire_uc");
            intent1.putExtra("trxid", trxid);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view -> {
            super.onBackPressed();
        });

    }

}