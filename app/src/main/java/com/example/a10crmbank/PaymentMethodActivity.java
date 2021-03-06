package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {

    Button point_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method);

        findViewById(R.id.back_imageview).setOnClickListener(View ->{
            super.onBackPressed();
        });

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        point_button =  findViewById(R.id.point);

        if(user_id==null || login_id==null){
            point_button.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        String transaction_type = intent.getStringExtra("transaction_type");

        if(transaction_type.matches("buy_point")){
            point_button.setVisibility(View.GONE);
        }

    }

    public void paymentMethod(View view) {
        Intent intent = getIntent();
        String mbank_id = intent.getStringExtra("mbank_id");
        String transaction_type = intent.getStringExtra("transaction_type");
        String selected_package = intent.getStringExtra("selected_package");
        String amount = intent.getStringExtra("amount");
        // BUY TPG
        String player_id = intent.getStringExtra("player_id");
        if(player_id==null){
            player_id = "0";
        }

        Integer  button_id = view.getId();
        String pay_method="default";

        if(button_id == R.id.rocket){
            pay_method = "rocket";
        }else if(button_id == R.id.bkash){
            pay_method = "bKash";
        }else if(button_id == R.id.nagad){
            pay_method = "nagad";
        }else if(button_id == R.id.point){
            pay_method = "point";
        }else if(button_id == R.id.upay){
            pay_method = "upay";
        }

        Intent pintent = new Intent(getApplicationContext(), PaymentConfirmActivity.class);
        pintent.putExtra("mbank_id",mbank_id);
        pintent.putExtra("amount",amount);
        pintent.putExtra("method",pay_method);
        pintent.putExtra("transaction_type",transaction_type);
        pintent.putExtra("selected_package",selected_package);
        pintent.putExtra("player_id",player_id);
        startActivity(pintent);
    }


}