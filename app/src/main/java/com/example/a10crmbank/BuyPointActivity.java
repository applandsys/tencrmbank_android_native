package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class BuyPointActivity extends AppCompatActivity {

    EditText point_amount_edittext;
    String point_amount;
    String post_login_id, post_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_points);
        point_amount_edittext= findViewById(R.id.point_amount_edittext);

    //    point_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("99", "2000")});

        point_amount_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(point_amount_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("প্লেয়ায়র আইডি", " এক সাথে 99-2000 Point এর বেশি কিনা যাবে না।");
                return true;
            }
        } );
        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        if(login_id == null || login_id.isEmpty()){
            post_login_id="0";
        }

        if(user_id == null || user_id.isEmpty()){
            post_user_id="0";
        }

        findViewById(R.id.btnBuy).setOnClickListener(view -> {
            point_amount = point_amount_edittext.getText().toString().trim();
            if(TextUtils.isEmpty(point_amount)){
                point_amount_edittext.setError("Enter Point Amount");
                point_amount_edittext.requestFocus();
                return;
            }

            if(Integer.parseInt(point_amount)<=99 || Integer.parseInt(point_amount)>=2000){
                point_amount_edittext.setError("Enter Amount 99-2000");
                point_amount_edittext.requestFocus();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
            intent.putExtra("transaction_type","buy_point");
            intent.putExtra("amount",point_amount);
            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }

    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(BuyPointActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(BuyPointActivity.this).inflate(R.layout.customview, viewGroup, false);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        TextView alert_title = dialogView.findViewById(R.id.alert_title);
        TextView alert_description = dialogView.findViewById(R.id.alert_description);
        alert_title.setText(title);
        alert_description.setText(description);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }



}