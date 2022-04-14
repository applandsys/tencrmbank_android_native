package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GiftCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_gift_card);

        findViewById(R.id.dollar_card).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), DollarCardActivity.class));
        });

        findViewById(R.id.rupee_card).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RupeCardActivity.class));
        });


        findViewById(R.id.back_imageview).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });
    }
}