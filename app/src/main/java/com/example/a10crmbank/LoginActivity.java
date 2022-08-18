package com.example.a10crmbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private View buy_chips, vip_card, gullak, gold_pass, gift_card, free_fire, pubg;
    private ImageButton home, settings, offer, info;
    private TextView password_reset_edittext;
    private String mobile_number_value , password_value;
    private EditText mobiel_number, loin_password ;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }


        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        home = findViewById(R.id.home);
        settings = findViewById(R.id.settings);
        offer = findViewById(R.id.offer);
        info = findViewById(R.id.info);
        mobiel_number = findViewById(R.id.mobile_number);
        loin_password = findViewById(R.id.loin_password);
        password_reset_edittext = findViewById(R.id.password_reset_edittext);

        mobiel_number.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(mobiel_number)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("মোবাইল  নম্বর", "যে মোবাইল  নম্বর দিয়ে সাইন আপ করেছিলেন তা লিখুন");
                return true;
            }
        } );


        loin_password.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(loin_password)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("লগিন পাসওয়ার্ড", "সাইনআপ করার সময় ব্যবহৃত পাসওয়ার্ড টি দিন");
                return true;
            }
        } );

        progressBar = findViewById(R.id.progressBar);

        home.setOnClickListener(view -> {
            if(user_id==null && login_id==null){
                openAlert();
            }
        });

        password_reset_edittext.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ResetpassActivity.class));
        });

        settings.setOnClickListener(view -> {
            if(user_id==null && login_id==null){
                openAlert();
            }else{
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

        offer.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), OfferActivity.class));
        });

        info.setOnClickListener(view -> {
            if(user_id==null && login_id==null){
                startActivity(new Intent(getApplicationContext(), AttentionActivity.class));
            }else{
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

        buy_chips = findViewById(R.id.buy_chips);
        vip_card = findViewById(R.id.vip_card);
        gullak = findViewById(R.id.gullak);
        gold_pass = findViewById(R.id.gold_pass);
        gift_card = findViewById(R.id.gift_card);
        free_fire = findViewById(R.id.free_fire);
        pubg = findViewById(R.id.pubg);

        Games games = new Games();

        findViewById(R.id.btn_login).setOnClickListener(view -> {
            mobile_number_value = mobiel_number.getText().toString().trim();
            password_value = loin_password.getText().toString();

            if (TextUtils.isEmpty(mobile_number_value)) {
                mobiel_number.setError("Please enter your Mobile");
                mobiel_number.requestFocus();
                return;
            }

            String regexString = "01+[0-9]{9}";

            if(!mobile_number_value.trim().matches(regexString))
            {
                mobiel_number.setError("Please enter correct format");
                mobiel_number.requestFocus();
            }

            StringRequest loginstringRequest = new StringRequest(Request.Method.POST,URLs.URL_LOGIN,
                    response -> {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String login_status = obj.getString("status");
                            String message = obj.getString("message");
                            if( login_status.matches("true") ){
                                Users userss = new Users(
                                        obj.getString("user_id"),
                                        obj.getString("playerid"),
                                        obj.getString("name"),
                                        obj.getString("loginid")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(userss);
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }else if(login_status.matches("multiple") ){
                                Intent intent =  new Intent(getApplicationContext(), OtpMultiloginActivity.class);
                                intent.putExtra("message",message);
                                startActivity(intent);
                            }else if(login_status.matches("fail")){
                                // alert dialog
                                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.CustomAlertDialog);
                                ViewGroup viewGroup = findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.customview, viewGroup, false);
                                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                                TextView alert_title = dialogView.findViewById(R.id.alert_title);
                                TextView alert_description = dialogView.findViewById(R.id.alert_description);
                                alert_title.setText("Login Failed");
                                alert_description.setText(obj.getString("message"));
                                builder.setView(dialogView);
                                final AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   Log.d("fuck","error ase cactch"+e.toString());
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    },error -> {

                    }
            )
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "login");
                    params.put("version", "1");
                    params.put("mobile_number", mobile_number_value);
                    params.put("passwords", password_value);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(loginstringRequest);

        });


        findViewById(R.id.txy_register).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });

        // Game gula check kora
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URLs.GAME_CHECK;
        StringRequest gamecheckRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray gamearr = new JSONArray(response);

                            int total_game = gamearr.length();

                            for(int i=0; i < total_game; i++ ){
                                JSONObject obj = gamearr.getJSONObject(i);

                                 String game_name = obj.getString("game_name");
                                 String is_active = obj.getString("is_active");

                                if( game_name.matches("tpg")  && is_active.matches("no")){
                                     games.setTpg("no");
                                }

                                if( game_name.matches("vip")  && is_active.matches("no")){
                                    games.setVip("no");
                                }

                                if( game_name.matches("gullak")  && is_active.matches("no")){
                                    games.setGullak("no");
                                }

                                if( game_name.matches("goldpass")  && is_active.matches("no")){
                                    games.setGoldpass("no");
                                }

                                if( game_name.matches("playcard")  && is_active.matches("no")){
                                    games.setPlaycard("no");
                                }

                                if( game_name.matches("freefire")  && is_active.matches("no")){
                                    games.setFreefire("no");
                                }

                                if( game_name.matches("pubg")  && is_active.matches("no")){
                                    games.setPubg("no");
                                }

                                if( game_name.matches("buy_chips") && is_active.matches("no")){
                                    games.setChips("no");
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Log.d("fuck","wanamarysalma"+e.toString());
                        }

                        String tpg_status = games.getTpg();
                        String vip_status = games.getVip();
                        String gullak_status = games.getGullak();
                        String giftcard_status = games.getPlaycard();
                        String goldpas_status = games.getGoldpass();
                        String pubg_status = games.getPubg();
                        String freefire_status = games.getFreefire();

                        buy_chips.setOnClickListener(view -> {
                            if(tpg_status.matches("yes")){
                                startActivity(new Intent(LoginActivity.this,BuyChips24hActivity.class));
                            }else{
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }
                        });
                        vip_card.setOnClickListener(view -> {
                            if(vip_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,IdVipActivity.class));
                            }
                        });

                        gullak.setOnClickListener(view -> {
                            if(gullak_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,GullakBreakActivity.class));
                            }
                        });

                        gift_card.setOnClickListener(view -> {
                            if(giftcard_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,GiftCardActivity.class));
                            }
                        });

                        gold_pass.setOnClickListener(view -> {
                            if(goldpas_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,GoldPassActivity.class));
                            }
                        });

                        pubg.setOnClickListener(view -> {
                            if(pubg_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,PubgActivity.class));
                            }
                        });

                        free_fire.setOnClickListener(view -> {
                            if(freefire_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(LoginActivity.this,FreeFireActivity.class));
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   Log.d("fuck",error.toString());
            }
        });

        queue.add(gamecheckRequest);

    }

    private void openAlert(){  // alert dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.customview, viewGroup, false);
        Button buttonOk=dialogView.findViewById(R.id.buttonOk);

        TextView alert_title = dialogView.findViewById(R.id.alert_title);
        TextView alert_description = dialogView.findViewById(R.id.alert_description);
        alert_title.setText("প্রবেশযোগ্য নয়");
        alert_description.setText("এই অপশনে যেতে লগিন অথবা রেজিষ্টার করুন");
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        // end alert
    }

    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.customview, viewGroup, false);
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