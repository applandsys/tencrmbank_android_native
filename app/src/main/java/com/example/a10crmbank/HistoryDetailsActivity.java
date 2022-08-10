package com.example.a10crmbank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_details);

        Users users = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String user_id = users.getUser_id();
        String login_id = users.getLoginid();

        recyclerview = findViewById(R.id.history_recyclerview);

        Intent intent = getIntent();
        String transaction_type = intent.getStringExtra("transaction_type");

        extractHistory(user_id,login_id);

        findViewById(R.id.back_imageview).setOnClickListener(view ->{
            super.onBackPressed();
        });

    }

    private void extractHistory(String user_id, String login_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URLs.HISTORY,
                response -> {

                    Log.d("fuck",response.toString());

                    try {
                        ArrayList<History> data = new ArrayList<>(); // eta ekta array list ja History Pojoclass type

                        JSONArray jsonArray =  new JSONArray(response);

                        final Integer itemcount = jsonArray.length();

                        for(Integer i = 0; i <itemcount; i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String myid =  jsonObject.getString("id");
                            String mytype=jsonObject.getString("type");
                            String myamount=jsonObject.getString("amount");
                            String mydate=jsonObject.getString("time_date");

                            History history = new History(); // setter or construction dia ekta object ceate hosse
                            history.setMyid(myid);
                            history.setMytype(mytype);
                            history.setMyamount(myamount);
                            history.setMydate(mydate);
                            data.add(history); // object ta array list e add hosse loop er modde

                            HistoryAdapter adapter = new HistoryAdapter(data);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerview.setLayoutManager(layoutManager);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());
                            recyclerview.setAdapter(adapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },error -> {
                //  Log.d("fuck",error.toString());
        })
        {
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params= new HashMap<>();
                params.put("user_id",user_id);
                params.put("login_id",login_id);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}