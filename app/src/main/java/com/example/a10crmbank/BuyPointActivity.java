package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BuyPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_points);

        findViewById(R.id.btnBuy).setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class));

        });


    }
}