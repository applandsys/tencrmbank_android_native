package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method);

        // create the get Intent object
        Intent intent = getIntent();

        String mbank_id = intent.getStringExtra("mbank_id");
        String point_amount = intent.getStringExtra("point_amount");

        findViewById(R.id.go_back).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });
    }

    public void paymentMethod(View view) {
        Intent intent = new Intent(getApplicationContext(), PaymentConfirmActivity.class);
        intent.putExtra("player_id","mypid");
        intent.putExtra("amount","myamount");
        startActivity(intent);
    }


}