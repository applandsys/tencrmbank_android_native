package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class BuyPointActivity extends AppCompatActivity {

    EditText point_amount_edittext;
    String point_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_points);
        point_amount_edittext= findViewById(R.id.point_amount_edittext);

        findViewById(R.id.btnBuy).setOnClickListener(view -> {
            point_amount = point_amount_edittext.getText().toString().trim();
            if(TextUtils.isEmpty(point_amount)){
                point_amount_edittext.setError("Enter Point Amount");
                point_amount_edittext.requestFocus();
                return;
            }
            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type","buy_point");
            intent.putExtra("amount",point_amount);
            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        });

    }



}