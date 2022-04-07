package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerIdChipsActivity extends AppCompatActivity {

    EditText player_id_edittext, chips_amount_edittext;
    String player_id, chips_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_in_player_id);
        player_id_edittext = findViewById(R.id.player_id_edittext);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);


        findViewById(R.id.btn_pay).setOnClickListener(view -> {

            player_id = player_id_edittext.getText().toString();
            chips_amount = chips_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Please put player ID");
                player_id_edittext.requestFocus();
                return;
            }
            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Please put Amount of Chips");
                chips_amount_edittext.requestFocus();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("player_id",player_id);
            intent.putExtra("amount",chips_amount);
            intent.putExtra("transaction_type","buy_tpg");
            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(View ->{
            super.onBackPressed();
        });

    }
}