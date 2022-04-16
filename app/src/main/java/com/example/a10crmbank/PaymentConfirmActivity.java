package com.example.a10crmbank;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class PaymentConfirmActivity extends AppCompatActivity {

    EditText trx_id_edittext ;
    TextView payment_instruction, hotline, notice, note;
    String mbank_id , amount , transaction_type, method,selected_package,player_id, login_id , user_id, trx_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_confirm);
        trx_id_edittext = findViewById(R.id.trx_id_edittext);
        payment_instruction =  findViewById(R.id.payment_instruction);
        hotline =  findViewById(R.id.hotline);
        notice =  findViewById(R.id.notice);
        note =  findViewById(R.id.note);


        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        user_id = users.getUser_id();
        login_id = users.getLoginid();

        if(user_id==null){
            user_id= "0";
        }

        Intent intent = getIntent();

         mbank_id = intent.getStringExtra("mbank_id");
         if(mbank_id==null){
             mbank_id = "0";
         }
         amount = intent.getStringExtra("amount");
        if(amount==null){
            amount = "0";
        }

         transaction_type = intent.getStringExtra("transaction_type");
         method = intent.getStringExtra("method");
         selected_package = intent.getStringExtra("selected_package");
        if(selected_package==null){
            selected_package = "0";
        }
         player_id = intent.getStringExtra("player_id");
        if(player_id==null){
            player_id = "0";
        }

        StringRequest stringRequest1 = new StringRequest(Request.Method.POST,URLs.PAYMENT_INSTRUCTION,
                    response -> {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String instruction_text = jsonObject.getString("message");
                            String hotline_text = jsonObject.getString("hotline");
                            String notice_text = jsonObject.getString("notice");
                            String note_text = jsonObject.getString("note");
                            payment_instruction.setText(instruction_text);
                            hotline.setText(hotline_text);
                            notice.setText(notice_text);
                            note.setText(note_text);

                        } catch (JSONException e) {
                            Log.d("fuck",e.toString());
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.d("fuck",error.toString());
                    }
                ){
                    protected Map<String,String> getParams() throws AuthFailureError{
                        Map<String,String> params = new HashMap<>();
                        params.put("mbank_id",mbank_id);
                        params.put("user_id",user_id);
                        params.put("amount",amount);
                        params.put("transaction_type",transaction_type);
                        params.put("method",method);
                        params.put("selected_package",selected_package);
                        params.put("player_id",player_id);
                        return params;
                    }
                 };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest1);


        findViewById(R.id.confirm_button).setOnClickListener((View v) -> {
            Log.d("fuck","btn click");

            trx_id = trx_id_edittext.getText().toString();
            if(TextUtils.isEmpty(trx_id)){
                trx_id_edittext.setError("Plese input TrxId");
                trx_id_edittext.requestFocus();
                return;
            }

            StringRequest stringRequestConfirm = new StringRequest(Request.Method.POST, URLs.PAYMENT_CONRIFM,
                    response -> {
                        Log.d("fuck",response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message_text = jsonObject.getString("message");
                            Log.d("fuck",message_text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.d("fuck",error.toString());
                    }){
                        protected Map<String,String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("trx_id",trx_id);
                            params.put("user_id",user_id);
                            params.put("mbank_id",mbank_id);
                            params.put("amount",amount);
                            params.put("transaction_type",transaction_type);
                            params.put("method",method);
                            params.put("selected_package",selected_package);
                            params.put("player_id",player_id);
                            return params;
                        }
                    };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequestConfirm);

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }
}