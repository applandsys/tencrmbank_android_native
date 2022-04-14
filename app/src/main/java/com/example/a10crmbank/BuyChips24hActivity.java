package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BuyChips24hActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_chip_24h);

        findViewById(R.id.btn_id_chips).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), PlayerIdChipsActivity.class));
        });

        findViewById(R.id.btn_bank_chips).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ChipsMinibankActivity.class));
        });

        findViewById(R.id.back_imageview).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });

    }
}