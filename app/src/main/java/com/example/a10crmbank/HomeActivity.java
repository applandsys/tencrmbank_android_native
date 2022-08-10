package com.example.a10crmbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class HomeActivity extends AppCompatActivity {

    private ImageButton home, settings, offer, info;
    private View buy_point, vip_card, buy_chips, gullak, gift_card, gold_pass, pubg, history;
    private TextView withdraw, gift, transfer, name_textview, balance_edittext,balancepoint_edittext;
    Boolean isLogin;

    Games games = new Games();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        home = findViewById(R.id.home);
        settings = findViewById(R.id.settings);
        offer = findViewById(R.id.offer);
        info = findViewById(R.id.info);
        name_textview = findViewById(R.id.name_textview);
        balance_edittext =  findViewById(R.id.balance_edittext);
        balancepoint_edittext =  findViewById(R.id.balancepoint_edittext);

        buy_point = findViewById(R.id.buy_point);
        vip_card = findViewById(R.id.vip_card);
        buy_chips = findViewById(R.id.buy_chips);
        gullak = findViewById(R.id.gullak);
        gift_card = findViewById(R.id.gift_card);
        gold_pass = findViewById(R.id.gold_pass);
        pubg = findViewById(R.id.pubg);
        history = findViewById(R.id.history);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        if(login_id == null || login_id.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        // Get Home page data from server
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.ACCOUNT_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            JSONArray userJson = obj.getJSONArray("playerdata");
                            String rawdata = userJson.getString(0);
                            JSONObject myobj = new JSONObject(rawdata);

                            name_textview.setText(myobj.getString("name"));
                            balance_edittext.setText("Chips: "+obj.getString("balance"));
                            balancepoint_edittext.setText("Point: "+obj.getString("balance_point"));

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
                params.put("action", "account_info");
                params.put("user_id", user_id);
                params.put("login_id", login_id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


        // Get Home Page data fromm server

        home.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"You are in your Panel Dashboard",Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });

        settings.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        });

        offer.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), OfferActivity.class));
        });

        info.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Attention2Activity.class));
        });

        withdraw = findViewById(R.id.withdraw);
        gift = findViewById(R.id.gift);
        transfer = findViewById(R.id.transfer);
        withdraw.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), WithdrawActivity.class));
        });
        gift.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GiftActivity.class));
        });
        transfer.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TransferActivity.class));
        });

        history.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
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

                                if( game_name.matches("pubg")  && is_active.matches("no")){
                                    games.setPubg("no");
                                }

                                if( game_name.matches("point")  && is_active.matches("no")){
                                    games.setPoint("no");
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
                        String point_status = games.getPoint();

                        buy_chips.setOnClickListener(view -> {
                            if(tpg_status.matches("yes")){
                                startActivity(new Intent(HomeActivity.this,BuyChips24hActivity.class));
                            }else{
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }
                        });
                        vip_card.setOnClickListener(view -> {
                            if(vip_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,IdVipActivity.class));
                            }
                        });

                        gullak.setOnClickListener(view -> {
                            if(gullak_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,GullakBreakActivity.class));
                            }
                        });

                        gift_card.setOnClickListener(view -> {
                            if(giftcard_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,GiftCardActivity.class));
                            }
                        });

                        gold_pass.setOnClickListener(view -> {
                            if(goldpas_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,GoldPassActivity.class));
                            }
                        });

                        pubg.setOnClickListener(view -> {
                            if(pubg_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,PubgActivity.class));
                            }
                        });

                        buy_point.setOnClickListener(view -> {
                            if(point_status.matches("no")){
                                showInfo("Alert!","এই সেবা টি এখন সচল নেই");
                            }else{
                                startActivity(new Intent(HomeActivity.this,BuyPointActivity.class));
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Log.d("fuck",error.toString());
            }
        });

        queue.add(gamecheckRequest);


    }


    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.customview, viewGroup, false);
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