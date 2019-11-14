package com.example.navhosv1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class AdapterMeeting extends RecyclerSwipeAdapter<AdapterMeeting.SimpleViewHolder> {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Callback<List<Data>> mContext;
    private ArrayList<Data> dataNextday;

    public AdapterMeeting(Callback<List<Data>> context, ArrayList<Data> objects) {
        this.mContext = context;
        this.dataNextday = objects;
    }

    @Override
    public AdapterMeeting.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nextdaylist_activity, parent, false);
        return new AdapterMeeting.SimpleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdapterMeeting.SimpleViewHolder viewHolder, final int position) {
        final Data item = dataNextday.get(position);

        viewHolder.Name.setText(item.getNo() +"");
        viewHolder.EmailId.setText("    "+item.getRoom());

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDB(item.getHn(),item.getDate(),item.getNo());
                System.out.println(item.getDate());
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                dataNextday.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataNextday.size());

                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "ลบ " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return dataNextday.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout swipeLayout;
        public TextView Name;
        public TextView EmailId;
        public TextView Delete;
        public TextView Edit;
        public TextView Share;
        public ImageButton btnLocation;
        public SimpleViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            Name = (TextView) itemView.findViewById(R.id.text_fn);
            EmailId = (TextView) itemView.findViewById(R.id.text_ln);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
//            Edit = (TextView) itemView.findViewById(R.id.Edit);
//            Share = (TextView) itemView.findViewById(R.id.Share);
//            btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
        }
    }
    public void deleteFromDB(int delhn,String deldate,int delno){

        Call<List<Data>> call = api.deleteRoomNoByHnDate(delhn,deldate,delno);
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                dataNextday = new ArrayList<>(response.body());
                for (Data d: dataNextday
                ) {
                    System.out.println(d.getNo());
                    notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                System.out.println("delete fail");

            }


        });


    }
}
