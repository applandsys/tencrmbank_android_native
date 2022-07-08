package com.example.a10crmbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private ArrayList<History> history;

    public HistoryAdapter(ArrayList<History> history) {
        this.history = history;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.histoy_view,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        String myid = history.get(position).getMyid();
        String mytype = history.get(position).getMytype();
        String myamount = history.get(position).getMyamount();
        String mydate = history.get(position).getMydate();
        holder.transaction_id.setText(myid);
        holder.chips_amount.setText(myamount);
        holder.transaction_type.setText(mytype);
        holder.transaction_date.setText(mydate);
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView transaction_id;
        TextView chips_amount;
        TextView transaction_type;
        TextView transaction_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_id = itemView.findViewById(R.id.transaction_id);
            chips_amount = itemView.findViewById(R.id.chips_amount);
            transaction_type = itemView.findViewById(R.id.transaction_type);
            transaction_date = itemView.findViewById(R.id.transaction_date);
        }
    }
}
