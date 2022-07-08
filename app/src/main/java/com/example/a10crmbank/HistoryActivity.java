package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }

    public void startHistory(View view) {

        Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);

        if(view.getId()==R.id.tpg_ll){
            intent.putExtra("transaction_type","tpg");
        }
        else if(view.getId()==R.id.gullakbreak){
            intent.putExtra("transaction_type","gullakbreak");
        }
        else if(view.getId()==R.id.gold_pass){
            intent.putExtra("transaction_type","gold_pass");
        }
        else if(view.getId()==R.id.playcard){
            intent.putExtra("transaction_type","playcard");
        }
        else if(view.getId()==R.id.point){
            intent.putExtra("transaction_type","point");
        }
        else if(view.getId()==R.id.vip){
            intent.putExtra("transaction_type","vip");
        }

        startActivity(intent);
    }



}