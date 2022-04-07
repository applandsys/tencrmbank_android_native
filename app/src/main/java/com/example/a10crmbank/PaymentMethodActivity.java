package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method);

        findViewById(R.id.back_imageview).setOnClickListener(View ->{
            super.onBackPressed();
        });

    }

    public void paymentMethod(View view) {
        Intent intent = getIntent();
        String mbank_id = intent.getStringExtra("mbank_id");
        String transaction_type = intent.getStringExtra("transaction_type");
        String selected_package = intent.getStringExtra("selected_package");
        String amount = intent.getStringExtra("amount");
        // BUY TPG
        String player_id = intent.getStringExtra("player_id");


        Integer  button_id = view.getId();
        String pay_method="default";


        if(button_id == R.id.rocket){
            pay_method = "rocket";
        }else if(button_id == R.id.bkash){
            pay_method = "bkash";
        }else if(button_id == R.id.nagad){
            pay_method = "nagad";
        }else if(button_id == R.id.point){
            pay_method = "point";
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