package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

public class IdVipActivity extends AppCompatActivity {

    Spinner spinner;
    Button submit_button;
    TextView player_id_edittext, pack_info_edittext,chips_amount_edittext,limit_info_edittext,expiry_info_edittext;
    String post_login_id, post_user_id;
    Boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_id);
        player_id_edittext =  findViewById(R.id.player_id_edittext);
        pack_info_edittext =  findViewById(R.id.pack_info_edittext);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);
        limit_info_edittext= findViewById(R.id.limit_info_edittext);
        expiry_info_edittext = findViewById(R.id.expiry_info_edittext);

        spinner = findViewById(R.id.packagespinner);
        submit_button = findViewById(R.id.submit);

        player_id_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(player_id_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("প্লেয়ায়র আইডি", "গেমসের সেটিংস এ প্লেয়ায়র আইডি থাকে।\n");
                return true;
            }
        } );

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        if(user_id == null || user_id.isEmpty()){
            post_login_id="0";
            post_user_id="0";
            isLogin = false;
        }else{
            isLogin = true;
        }

        if(login_id == null || login_id.isEmpty()){
            post_login_id="0";
            post_user_id="0";
            isLogin = false;
        }else{
            isLogin = true;
        }

     //  ArrayList<Dropdown> packages = new ArrayList<Dropdown>();
        ArrayList<String> names = new ArrayList<String>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.VIPPACKAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray arr = new JSONArray(response);
                            names.add("Select a VIP Package");
                            for(int i=0; i< arr.length();i++){
                                JSONObject obj = arr.getJSONObject(i);
                                names.add(obj.getString("name"));
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.package_list,R.id
                                    .package_item,names);
                            spinner.setAdapter(adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                    if(position!=0){
                                        findViewById(R.id.info_box).setVisibility(View.VISIBLE);
                                        String package_id = String.valueOf(position);
                                       getVIPDetail(package_id);
                                    }else{
                                        findViewById(R.id.info_box).setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
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
                params.put("request_type", "vip_package");
                params.put("version", "2");
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        submit_button.setOnClickListener(view -> {
           String player_id = player_id_edittext.getText().toString();
           if(TextUtils.isEmpty(player_id)){
               player_id_edittext.setError("Put player Id");
               player_id_edittext.requestFocus();
               return;
           }

           String regexString = "[A-Za-z]{4}[0-9]{3}|100+[0-9]{6,20}";

           if(!player_id.trim().matches(regexString))
           {
               player_id_edittext.setError("Please enter correct format");
               player_id_edittext.requestFocus();
               return;
           }

           Integer ide =spinner.getSelectedItemPosition();
           String selected_item =  ide.toString();

           // check player id
           StringRequest stringRequestcheck = new StringRequest(Request.Method.POST,URLs.BUY_CONFIRM,
                   response -> {

                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           String status = jsonObject.getString("status");
                           String title = jsonObject.getString("title");
                           String message_text = jsonObject.getString("message");

                           // alert dialog
                           final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomAlertDialog);
                           ViewGroup viewGroup = findViewById(android.R.id.content);
                           View dialogView = LayoutInflater.from(this).inflate(R.layout.customview_confirm, viewGroup, false);
                           Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                           Button buttonCancel= dialogView.findViewById(R.id.buttonCancel);

                           TextView alert_title = dialogView.findViewById(R.id.alert_title);
                           TextView alert_description = dialogView.findViewById(R.id.alert_description);
                           alert_title.setText(title);
                           alert_description.setText(message_text);
                           builder.setView(dialogView);
                           final AlertDialog alertDialog = builder.create();
                           alertDialog.show();
                           buttonOk.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   alertDialog.dismiss();
                                   Intent intent =  new Intent(getApplicationContext(), PaymentMethodActivity.class);
                                   intent.putExtra("transaction_type","vip_buy");
                                   intent.putExtra("player_id",player_id);
                                   intent.putExtra("selected_package",selected_item);
                                   startActivity(intent);
                               }
                           });
                           buttonCancel.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   alertDialog.dismiss();
                                   return;
                               }
                           });

                       } catch (JSONException e) {
                           e.printStackTrace();
                          // Log.d("fuck", "Catch dekho:"+e.toString());
                       }
                   },error -> {
                       // Log.d("fuck","Error dekho:"+error.toString());
                   }
           ){
             protected Map<String,String> getParams() throws AuthFailureError{
                 Map<String,String> params = new HashMap<>();
                 params.put("transaction_type","vip_buy");
                 params.put("selected_package",selected_item);
                 params.put("player_id",player_id);
                 return params;
             }
           };

           VolleySingleton.getInstance(this).addToRequestQueue(stringRequestcheck);

       });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }

    private void getVIPDetail(String package_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.VIP_DETAIL,
                    response ->{
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                           // Log.d("fuck",jsonObject.toString());
                            String pack = jsonObject.getString("pack");
                            String chips = jsonObject.getString("chips");
                            String limit = jsonObject.getString("limit");
                            String expiry = jsonObject.getString("expiry");
                            pack_info_edittext.setText(pack);
                            chips_amount_edittext.setText(chips+" CR গেমসের ব্যাংকে থাকবে");
                            limit_info_edittext.setText(limit+" CR পর্যন্ত ");
                            expiry_info_edittext.setText("অ্যাক্টিভ এর সময় থেকে "+expiry+ "  দিন");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    },
                    error -> {

                    }
               ){
                     protected Map<String,String> getParams() throws AuthFailureError{
                            Map<String,String> params =  new HashMap<>();
                            params.put("package_id",package_id);
                            return params;
                    }
                };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(IdVipActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(IdVipActivity.this).inflate(R.layout.customview, viewGroup, false);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        TextView alert_title = dialogView.findViewById(R.id.alert_title);
        TextView alert_description = dialogView.findViewById(R.id.alert_description);
        alert_title.setText(title);
        alert_description.setText(description);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}