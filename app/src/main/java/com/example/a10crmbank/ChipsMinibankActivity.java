package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChipsMinibankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minibank_chips);

        findViewById(R.id.btn_pay).setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), PaymentMethod2Activity.class));

        });


    }
}