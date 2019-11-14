package com.example.navhosv1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterAllMeet extends RecyclerSwipeAdapter<AdapterAllMeet.ViewHolder> {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Context context;
    private ArrayList<Data> data = new ArrayList<>();
    private int id;
    private int hn;

    public AdapterAllMeet(Context context, ArrayList<Data> data,int id,int hn) {
        this.context = context;
        this.data = data;
        this.id = id;
        this.hn = hn;
    }

    @Override
    public AdapterAllMeet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_meet, parent, false);
        return new AdapterAllMeet.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterAllMeet.ViewHolder viewHolder, final int i) {
        final Data item = data.get(i);

        viewHolder.listdate.setText(item.getDate());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

       // viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));


        viewHolder.deletelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delGroupMeet(hn,item.getDate());
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                data.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, data.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "ลบรายการวันที่ " + viewHolder.listdate.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.listdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,"Date : "+ item.getDate(),Toast.LENGTH_SHORT).show();
                Context ct = view.getContext();

                Intent intent = new Intent(ct,MeetingDateByDate.class);
                intent.putExtra("date",item.getDate());
                intent.putExtra("id",id);
                ct.startActivity(intent);

            }
        });
        mItemManger.bindView(viewHolder.itemView,i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        private TextView listdate;
        private  TextView deletelist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            listdate = (TextView) itemView.findViewById(R.id.txt_alldatemeet);
            deletelist = (TextView) itemView.findViewById(R.id.Delete);

        }
    }

    public void delGroupMeet(int hn,String date){
        System.out.println(hn + "," + date);
        Call<List<Data>> call = api.delGmeet(hn,date);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }
}
