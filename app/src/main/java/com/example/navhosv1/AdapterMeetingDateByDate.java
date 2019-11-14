package com.example.navhosv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterMeetingDateByDate extends RecyclerSwipeAdapter<AdapterMeetingDateByDate.ViewHolder> {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Context context;
    private ArrayList<Data> datameetdate = new ArrayList<>();

    public AdapterMeetingDateByDate(Context context,ArrayList<Data> datameetdate) {
        this.context = context;
        this.datameetdate = datameetdate;
    }

    @Override
    public AdapterMeetingDateByDate.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meetdate,parent,false);
        return new AdapterMeetingDateByDate.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterMeetingDateByDate.ViewHolder viewHolder, final int i) {
        final Data item = datameetdate.get(i);

        viewHolder.no.setText(item.getNo()+"");
        viewHolder.room.setText(item.getRoom()+"");
        viewHolder.swipeLayout.setSwipeEnabled(false);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));
//        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(item.getHn() +"," +item.getDate()+","+item.getNo());
//             //   deleteFromDB(item.getHn(),item.getDate(),item.getNo());
//                System.out.println(item.getDate());
//                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                datameetdate.remove(i);
//                notifyItemRemoved(i);
//                notifyItemRangeChanged(i, datameetdate.size());
//
//                mItemManger.closeAllItems();
//                Toast.makeText(v.getContext(), "ลบ " + viewHolder.room.getText().toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


      //  mItemManger.bindView(viewHolder.itemView,i);
    }

    @Override
    public int getItemCount() {
        return datameetdate.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        private TextView no;
        private TextView room;
        //private  TextView Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            no = (TextView) itemView.findViewById(R.id.txt_no);
            room = (TextView) itemView.findViewById(R.id.txt_room);
           // Delete = (TextView) itemView.findViewById(R.id.Delete);

        }
    }
//    public void deleteFromDB(int delhn,String deldate,int delno){
//
//        Call<List<Data>> call = api.deleteRoomNoByHnDate(delhn,deldate,delno);
//        call.enqueue(new Callback<List<Data>>() {
//
//            @Override
//            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
//                datameetdate = new ArrayList<>(response.body());
//                for (Data d: datameetdate
//                ) {
//                    System.out.println(d.getNo());
//                    notifyDataSetChanged();
//                }
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<Data>> call, Throwable t) {
//                System.out.println("delete fail");
//
//            }
//
//
//        });
//
//
//    }
}