package com.example.a10crmbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText name_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        findViewById(R.id.btn_register).setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        });

        findViewById(R.id.btn_login).setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        });

    }
}