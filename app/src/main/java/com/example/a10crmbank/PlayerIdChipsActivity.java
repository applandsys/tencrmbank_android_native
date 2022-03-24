package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerIdChipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_in_player_id);

        findViewById(R.id.btn_pay).setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class));

        });
    }
}