package com.example.a10crmbank;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    String post_login_id, post_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gullak_break);
        spinner = findViewById(R.id.packagespinner);
        button = findViewById(R.id.submit);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        if(login_id == null || login_id.isEmpty()){
            post_login_id="0";
        }

        if(user_id == null || user_id.isEmpty()){
            post_user_id="0";
        }

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

            String regexString = "[A-Za-z]{4}[0-9]{3}|100+[0-9]{5,12}";

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
                                    intent.putExtra("transaction_type","gullak_break");
                                    intent.putExtra("player_id",player_id);
                                    intent.putExtra("selected_package",selected_item);
                                    startActivity(intent);
                                }
                            });
                            buttonCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                  //  startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },error -> {
                Log.d("fuck",error.toString());
            }
            ){
                protected Map<String,String> getParams() throws AuthFailureError{
                    Map<String,String> params = new HashMap<>();
                    params.put("transaction_type","gullak_break");
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
}