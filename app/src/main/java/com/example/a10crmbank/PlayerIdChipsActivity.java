package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlayerIdChipsActivity extends AppCompatActivity {

    EditText player_id_edittext, chips_amount_edittext;
    String player_id, chips_amount;
    String post_login_id, post_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_in_player_id);
        player_id_edittext = findViewById(R.id.player_id_edittext);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);

        player_id_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(player_id_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("প্লেয়ায়র আইডি", "গেমসের সেটিংস এ প্লেয়ায়র আইডি থাকে");
                return true;
            }
        } );

        chips_amount_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(chips_amount_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("লিমিট", "এক সাথে 1-100CR এর বেশি কিনা যাবে না");
                return true;
            }
        } );

        chips_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")});

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();
        if(login_id == null || login_id.isEmpty()){
            post_login_id="0";
        }

        if(user_id == null || user_id.isEmpty()){
            post_user_id="0";
        }

        findViewById(R.id.btn_pay).setOnClickListener(view -> {


            player_id = player_id_edittext.getText().toString();
            chips_amount = chips_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Please put player ID");
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

            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Please put Amount of Chips");
                chips_amount_edittext.requestFocus();
                return;
            }

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
                                    intent.putExtra("transaction_type","player_id_chips");
                                    intent.putExtra("player_id",player_id);
                                    intent.putExtra("amount",chips_amount);
                                    startActivity(intent);
                                }
                            });
                            buttonCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                   // startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },error -> {
                Log.d("fuck",error.toString());
            }
            ){
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("transaction_type","player_id_chips");
                    params.put("player_id",player_id);
                    params.put("amount",chips_amount);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequestcheck);

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });
    }

    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(PlayerIdChipsActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(PlayerIdChipsActivity.this).inflate(R.layout.customview, viewGroup, false);
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