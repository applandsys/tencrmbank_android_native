package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);

        findViewById(R.id.btn_point).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TransferPointActivity.class));
        });

        findViewById(R.id.btn_chips).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), TransferChipsActivity.class));
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }



}