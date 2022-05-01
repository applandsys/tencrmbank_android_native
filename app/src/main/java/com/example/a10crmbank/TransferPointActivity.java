package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TransferPointActivity extends AppCompatActivity {

    private EditText mbank_id_edittext,point_amount_edittext;

    private String mbank_id, point_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_point);
        mbank_id_edittext =  findViewById(R.id.mbank_id_edittext);
        point_amount_edittext = findViewById(R.id.point_amount_edittext);

        findViewById(R.id.confirm_button).setOnClickListener(view ->{
            mbank_id = mbank_id_edittext.getText().toString();
            point_amount = point_amount_edittext.getText().toString();

            if(TextUtils.isEmpty(mbank_id)){
                mbank_id_edittext.setError("Please enter mbank ID");
                mbank_id_edittext.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(point_amount)){
                point_amount_edittext.setError("Please enter point amount");
                point_amount_edittext.requestFocus();
                return;
            }

            Intent intent = new Intent(getApplicationContext(),PaymentMethodActivity.class);
            intent.putExtra("mbank_id",mbank_id);
            intent.putExtra("point_amount",point_amount);
            intent.putExtra("transaction_type","transfer_point");

            startActivity(intent);
        });

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });


    }
}