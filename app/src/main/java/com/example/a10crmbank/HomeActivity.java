package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

                            name_textview.setText(myobj.getString("name") );
                            balance_edittext.setText("Chips: "+obj.getString("balance") + " CR");
                            balancepoint_edittext.setText("Point: "+obj.getString("balance_point"));

                        } catch (JSONException e) {
                            Log.d("fuck", e.toString());
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


        buy_point = findViewById(R.id.buy_point);
        vip_card = findViewById(R.id.vip_card);
        buy_chips = findViewById(R.id.buy_chips);
        gullak = findViewById(R.id.gullak);
        gift_card = findViewById(R.id.gift_card);
        gold_pass = findViewById(R.id.gold_pass);
        pubg = findViewById(R.id.pubg);
        history = findViewById(R.id.history);

        setListener(buy_point, BuyPointActivity.class);
        setListener(vip_card, IdVipActivity.class);
        setListener(buy_chips, BuyChips24hActivity.class);
        setListener(gullak, GullakBreakActivity.class);
        setListener(gift_card, GiftCardActivity.class);
        setListener(gold_pass, GoldPassActivity.class);
        setListener(pubg, PubgActivity.class);
        setListener(history, HistoryActivity.class);

        // Game gula check kora
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URLs.GAME_CHECK;
        StringRequest gamecheckRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fuck",response.toString());
                        try {
                            JSONArray gamearr = new JSONArray(response);

                            int total_game = gamearr.length();

                            for(int i=0; i < total_game; i++ ){
                                JSONObject obj = gamearr.getJSONObject(i);

                                String game_name = obj.getString("game_name");
                                String is_active = obj.getString("is_active");

                                if( game_name.matches("tpg")  && is_active.matches("no")){
                                    gift_card.setVisibility(View.INVISIBLE);
                                }

                                if( game_name.matches("vip")  && is_active.matches("no")){
                                    vip_card.setVisibility(View.INVISIBLE);
                                }

                                if( game_name.matches("gullak")  && is_active.matches("no")){
                                    gullak.setVisibility(View.INVISIBLE);
                                }

                                if( game_name.matches("goldpass")  && is_active.matches("no")){
                                    gold_pass.setVisibility(View.INVISIBLE);
                                }

                                if( game_name.matches("playcard")  && is_active.matches("no")){
                                    gift_card.setVisibility(View.INVISIBLE);
                                }

                                if( game_name.matches("pubg")  && is_active.matches("no")){
                                    pubg.setVisibility(View.INVISIBLE);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Log.d("fuck","wanamarysalma"+e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("fuck",error.toString());
            }
        });

        queue.add(gamecheckRequest);

        // game gula check kora end


    }

    private void setListener(View v,Class<?> destination){
        v.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), destination)));
    }



}