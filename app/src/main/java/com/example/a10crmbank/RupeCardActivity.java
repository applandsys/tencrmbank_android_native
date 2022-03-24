package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RupeCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rupee_card);
        findViewById(R.id.submit).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class)));

    }
}