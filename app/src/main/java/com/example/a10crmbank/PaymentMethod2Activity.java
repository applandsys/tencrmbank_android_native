package com.example.a10crmbank;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentMethod2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method_2);

        findViewById(R.id.back_imageview).setOnClickListener(View ->{
            super.onBackPressed();
        });

    }
}