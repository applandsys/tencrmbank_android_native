package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GoldPassActivity extends AppCompatActivity {

    EditText player_id_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gold_pass);
        player_id_edittext = findViewById(R.id.player_id_edittext);

        findViewById(R.id.submit).setOnClickListener(view -> {
            String player_id = player_id_edittext.getText().toString();
            if(TextUtils.isEmpty(player_id)){
                player_id_edittext.setError("Put player Id");
                player_id_edittext.requestFocus();
                return;
            }
            Intent intent =  new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type","goldpass");
            intent.putExtra("player_id",player_id);
            startActivity(intent);

        });

    }
}