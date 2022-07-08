package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class WithdrawActivity extends AppCompatActivity {

    EditText withdraw_input_edittext;
    String withdraw_input_value;
    TextView alert_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw);
        withdraw_input_edittext = findViewById(R.id.withdraw_input_edittext);
        alert_text =  findViewById(R.id.alert_text);

        withdraw_input_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "50")});

        withdraw_input_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(withdraw_input_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("5-50CR ", "এক সাথে 1-50CR এর বেশি কিনা যাবে না।");
                return true;
            }
        } );

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        findViewById(R.id.withdraw_button).setOnClickListener(view ->{

            withdraw_input_value =    withdraw_input_edittext.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.WITHDRAAW,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                alert_text.setText(obj.getString("message"));

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
                    params.put("transaction_type", "withdraw");
                    params.put("version", "2");
                    params.put("chips", withdraw_input_value);
                    params.put("userid", user_id);
                    params.put("loginid", login_id);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        });


        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }

    private void showInfo(String title, String description){
        final AlertDialog.Builder builder = new AlertDialog.Builder(WithdrawActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(WithdrawActivity.this).inflate(R.layout.customview, viewGroup, false);
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