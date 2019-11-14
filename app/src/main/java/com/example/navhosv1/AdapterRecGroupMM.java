package com.example.navhosv1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecGroupMM extends RecyclerView.Adapter<AdapterRecGroupMM.ViewHolder> {

    private Context context;
    private ArrayList<DataGroupMM> dataGroupMMS;
    private int idlogin;

    public AdapterRecGroupMM(Context context, ArrayList<DataGroupMM> dataGroupMMS) {
        this.context = context;
        this.dataGroupMMS = dataGroupMMS;
    }

    public AdapterRecGroupMM(int idlogin) {
        this.idlogin = idlogin;
    }

    @NonNull
    @Override
    public AdapterRecGroupMM.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new AdapterRecGroupMM.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecGroupMM.ViewHolder holder, int position) {
            final DataGroupMM item = dataGroupMMS.get(position);

            holder.txtdate.setText(item.getDate()+"");

            holder.txtdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context contextItem = view.getContext();
                    Intent i = new Intent(contextItem,ListNoItem.class);
                    i.putExtra("idlogin",idlogin);
                    i.putExtra("Msel",item.getDate());
                    contextItem.startActivity(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return dataGroupMMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdate = (TextView) itemView.findViewById(R.id.txt_date);
        }
    }
}
