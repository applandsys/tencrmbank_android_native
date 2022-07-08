package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransferPointActivity extends AppCompatActivity {

    private EditText mbank_id_edittext,point_amount_edittext;

    private String mbank_id, point_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_point);
        mbank_id_edittext =  findViewById(R.id.mbank_id_edittext);
        point_amount_edittext = findViewById(R.id.point_amount_edittext);

        point_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("100", "500")});


        point_amount_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(point_amount_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("5-50CR ", "এক সাথে 100-500Point এর বেশি কিনা যাবে না।");
                return true;
            }
        } );

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        findViewById(R.id.confirm_button).setOnClickListener(view ->{
            mbank_id = mbank_id_edittext.getText().toString();
            point_amount = point_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(mbank_id)){
                mbank_id_edittext.setError("Please enter mbank ID");
                mbank_id_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(point_amount)){
                point_amount_edittext.setError("Please enter point amount");
                point_amount_edittext.requestFocus();
                return;
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.TRANSFER_POINT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
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
                    params.put("transaction_type", "transfer_point");
                    params.put("version", "2");
                    params.put("point_amount", point_amount);
                    params.put("userid", user_id);
                    params.put("loginid", login_id);
                    params.put("player_id", mbank_id);
                    params.put("company_id", mbank_id);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(TransferPointActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(TransferPointActivity.this).inflate(R.layout.customview, viewGroup, false);
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