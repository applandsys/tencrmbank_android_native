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

public class ChipsMinibankActivity extends AppCompatActivity {

    EditText chips_amount_edittext;
    String chips_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minibank_chips);
        chips_amount_edittext = findViewById(R.id.chips_amount_edittext);

      //  chips_amount_edittext.setFilters(new InputFilter[]{ new InputFilterMinMax("5", "150")});

        chips_amount_edittext.setOnTouchListener( new DrawableClickListener.RightDrawableClickListener(chips_amount_edittext)
        {
            @Override
            public boolean onDrawableClick()
            {
                showInfo("Chips Amount", " এক সাথে 5-150 কিনা যাবে");
                return true;
            }
        } );

        findViewById(R.id.btn_pay).setOnClickListener(view -> {

            chips_amount = chips_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(chips_amount)){
                chips_amount_edittext.setError("Please put Amount of Chips");
                chips_amount_edittext.requestFocus();
                return;
            }

            if(Integer.parseInt(chips_amount)< 5 || Integer.parseInt(chips_amount) > 150){
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

    private void showInfo(String title, String description){
        // TODO : insert code to perform on clicking of the RIGHT drawable image...
        final AlertDialog.Builder builder = new AlertDialog.Builder(ChipsMinibankActivity.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ChipsMinibankActivity.this).inflate(R.layout.customview, viewGroup, false);
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