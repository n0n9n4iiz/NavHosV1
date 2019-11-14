package com.example.navhosv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdepterHislist extends RecyclerView.Adapter<AdepterHislist.ViewHolder> {

    private ArrayList<Data> itemlist;
    private Context context;

    public AdepterHislist(ArrayList<Data> itemlist, Context context) {
        this.itemlist = itemlist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdepterHislist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_no_item,parent,false);
        return new AdepterHislist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdepterHislist.ViewHolder holder, int position) {
            Data item = itemlist.get(position);
            holder.no.setText(item.getNo()+"");
            holder.room.setText(item.getRoom()+"");
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView no;
        private TextView room;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.no);
            room = (TextView) itemView.findViewById(R.id.room);
        }
    }
}
