package com.example.navhosv1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);

    int id;
    private Context mContext;
    static ArrayList<Data> studentList;


    public SwipeRecyclerViewAdapter(Context context, ArrayList<Data> objects) {
        this.mContext = context;
        this.studentList = objects;

    }

    public SwipeRecyclerViewAdapter(int id) {
        this.id = id;
    }



    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Data item = studentList.get(position);

        viewHolder.Name.setText(item.getNo() +"");
        viewHolder.EmailId.setText("    "+item.getRoom());

        if(item.getSuccess() == null){

        }else{
            viewHolder.itemView.setBackgroundResource(R.color.colorSuccess);
            viewHolder.Name.setBackgroundResource(R.drawable.overalnumsucc);
            viewHolder.swipeLayout.setSwipeEnabled(false);
        }

        DataRoomseq dr = new DataRoomseq();
        dr.setNo(item.getNo());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kiri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));




        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(item.getRoom());
               // Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                Context context = view.getContext();
                Intent intent = new Intent(context, SelectCurrentLocation.class);
                intent.putExtra("room",item.getRoom());
                context.startActivity(intent);
            }
        });

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               viewHolder.itemView.setBackgroundResource(R.color.colorSuccess);
               viewHolder.Name.setBackgroundResource(R.drawable.overalnumsucc);
               setSucc(item.getHn(),item.getNo(),item.getDate());
               viewHolder.swipeLayout.close();
               viewHolder.swipeLayout.setSwipeEnabled(false);
                   Toast.makeText(view.getContext(), "Clicked on Edit  " + item.getNo(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromDB(item.getHn(),item.getDate(),item.getNo());
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                studentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentList.size());

                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "ลบ " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return studentList.size();
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
        public SimpleViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            Name = (TextView) itemView.findViewById(R.id.Name);
            EmailId = (TextView) itemView.findViewById(R.id.EmailId);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
            Edit = (TextView) itemView.findViewById(R.id.Edit);
            Share = (TextView) itemView.findViewById(R.id.Share);
//            btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
        }
    }
    public void deleteFromDB(int delhn,String deldate,int delno){

        Call<List<Data>> call = api.deleteRoomNoByHnDate(delhn,deldate,delno);
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                studentList = new ArrayList<>(response.body());
                for (Data d: studentList
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


    public void setSucc(int hn,int no ,String date){

        Data success = new Data("success");
        Call<Data> call = api.getSuccess(hn,no,date,success);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                System.out.println("success");

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                System.out.println("fail");
            }

    });


}
}
