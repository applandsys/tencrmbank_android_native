package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BuyChips24hActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_chip_24h);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();


        findViewById(R.id.btn_id_chips).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), PlayerIdChipsActivity.class));
        });

        findViewById(R.id.btn_bank_chips).setOnClickListener(view -> {

            if(login_id == null || login_id.isEmpty()){
                final AlertDialog.Builder builder = new AlertDialog.Builder(BuyChips24hActivity.this,R.style.CustomAlertDialog);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(BuyChips24hActivity.this).inflate(R.layout.customview, viewGroup, false);
                Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                TextView alert_title = dialogView.findViewById(R.id.alert_title);
                TextView alert_description = dialogView.findViewById(R.id.alert_description);
                alert_title.setText("সতর্কতা!");
                alert_description.setText("এই ফিচারটি ব্যবহার করতে আগে লগিন করুন");
                builder.setView(dialogView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                });

            }else{
                startActivity(new Intent(getApplicationContext(), ChipsMinibankActivity.class));
            }

        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });


    }
}