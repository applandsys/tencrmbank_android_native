package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentConfirmActivity extends AppCompatActivity {

    EditText trx_id_edittext ;
    String trx_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirm);
        trx_id_edittext = findViewById(R.id.trx_id_edittext);

        Intent intent = getIntent();

        String mbank_id = intent.getStringExtra("mbank_id");
        String amount = intent.getStringExtra("point_amount");
        String transfer_type = intent.getStringExtra("transfer_type");
        String method = intent.getStringExtra("method");
        String selected_package = intent.getStringExtra("selected_package");

        Log.d("fuck","marry Tania : "+selected_package);

        findViewById(R.id.confirm_button).setOnClickListener((View v) -> {

            Log.d("fuck","Confirm to :"+method);
        /*
            trx_id = trx_id_edittext.getText().toString();

            if(TextUtils.isEmpty(trx_id)){
                trx_id_edittext.setError("Plese input TrxId");
                trx_id_edittext.requestFocus();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TRANSFER,
                    response -> {

                    },
                    error -> {

                    }){
                        protected Map<String,String> getParam() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("trx_id",trx_id);
                            params.put("mbank_id",mbank_id);
                            params.put("amount",amount);
                            params.put("transfer_type",transfer_type);
                            params.put("method",method);
                            return params;
                        }
                    };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

            */

        });

    }
}