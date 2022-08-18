package com.example.a10crmbank;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        findViewById(R.id.facebok_page).setOnClickListener(view ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.FACEBOOK));
            startActivity(browserIntent);
        });

        findViewById(R.id.youtube).setOnClickListener(view ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.YOUTUBE));
            startActivity(browserIntent);
        });

        findViewById(R.id.messenger).setOnClickListener(view ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.MESSENGER));
            startActivity(browserIntent);
        });

        findViewById(R.id.review).setOnClickListener(view ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.REVIEW));
            startActivity(browserIntent);
        });

        findViewById(R.id.comlain).setOnClickListener(view ->{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.COMPLAIN));
            startActivity(browserIntent);
        });



        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }


}