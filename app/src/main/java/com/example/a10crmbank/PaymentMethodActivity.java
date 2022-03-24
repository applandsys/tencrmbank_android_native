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
    }

    public void paymentMethod(View view) {

        startActivity(new Intent(getApplicationContext(), PaymentConfirmActivity.class));

    }
}