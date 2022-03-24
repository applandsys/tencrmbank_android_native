package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GoldPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_pass);
        findViewById(R.id.submit).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class)));

    }
}