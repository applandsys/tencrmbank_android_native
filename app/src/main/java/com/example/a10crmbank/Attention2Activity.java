package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Attention2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention_2);

        findViewById(R.id.btn_mbank).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ChipsMinibankActivity.class));
        });

        findViewById(R.id.setting_back_imagevie).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });
    }
}