package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ChipsMinibankActivity extends AppCompatActivity {

    EditText chips_amount_edittext;
    String chips_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minibank_chips);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);

      //  chips_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("5", "150")});

        findViewById(R.id.btn_pay).setOnClickListener(view -> {

            chips_amount = chips_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Please put Amount of Chips");
                chips_amount_edittext.requestFocus();
                return;
            }

            if(Integer.parseInt(chips_amount)<=5 || Integer.parseInt(chips_amount)>=150){
                chips_amount_edittext.setError("Enter Amount 5-150");
                chips_amount_edittext.requestFocus();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("amount",chips_amount);
            intent.putExtra("transaction_type","minibank_chips");
            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });



    }
}