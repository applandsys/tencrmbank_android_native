package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TransferChipsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_chips);
        findViewById(R.id.confirm_button).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), PaymentMethodActivity.class)));

    }
}