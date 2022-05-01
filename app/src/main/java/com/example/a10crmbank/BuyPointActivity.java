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


        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

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
            intent.putExtra("user_id",user_id);
            intent.putExtra("login_id",login_id);
            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }



}