package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private View buy_chips, vip_card, gullak, gold_pass, gift_card, free_fire, pubg;
    private ImageButton home, settings, offer, info;

    // for login
    private String mobile_number_value , password_value;
    private EditText mobiel_number, loin_password;

    ProgressBar progressBar;

    SharedPreferences sharedPref;
    private final String SHARED_PREF_NAME = "mypref";
    private final String KEY_PLAYERID = "playerid";
    private final String KEY_USERID = "user_id";
    private final String KEY_NAME = "name";

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

        progressBar = findViewById(R.id.progressBar);

        home.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Register/Login First", Toast.LENGTH_SHORT).show();
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

        buy_chips = findViewById(R.id.buy_chips);
        vip_card = findViewById(R.id.vip_card);
        gullak = findViewById(R.id.gullak);
        gold_pass = findViewById(R.id.gold_pass);
        gift_card = findViewById(R.id.gift_card);
        free_fire = findViewById(R.id.free_fire);
        pubg = findViewById(R.id.pubg);

        buy_chips.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), BuyChips24hActivity.class));
        });

        vip_card.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), IdVipActivity.class));
        });

        gullak.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GullakBreakActivity.class));
        });

        gold_pass.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GoldPassActivity.class));
        });

        gift_card.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), GiftCardActivity.class));
        });

        free_fire.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), FreeFireActivity.class));
        });

        pubg.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), PubgActivity.class));
        });


        findViewById(R.id.btn_login).setOnClickListener(view -> {
            mobile_number_value = mobiel_number.getText().toString().trim();
            password_value = loin_password.getText().toString();



            if (TextUtils.isEmpty(mobile_number_value)) {
                mobiel_number.setError("Please enter your Mobile");
                mobiel_number.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(mobile_number_value)) {
                loin_password.setError("Please enter your Password");
                loin_password.requestFocus();
                return;
            }

                    //if everything is fine
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);

                                    try {
                                        //converting response to json object
                                        JSONObject obj = new JSONObject(response);

                                        Users users = new Users(
                                                obj.getString("user_id"),
                                                obj.getString("playerid"),
                                                obj.getString("name"),
                                                obj.getString("loginid")
                                        );

                                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(users);

                                        if( obj.getString("login")=="true" || obj.getString("status")=="true" ){
                                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        }
                                        finish();

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
                            params.put("mobile_number", mobile_number_value);
                            params.put("passwords", password_value);
                            return params;
                        }
                    };

                    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

        });

        findViewById(R.id.txy_register).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });


    }

}