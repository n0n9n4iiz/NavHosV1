package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyActivity extends AppCompatActivity {

    Context context;
    private TextView tvEmptyTextView;
    public static RecyclerView mRecyclerView;
    private ArrayList<Data> mDataSet;
    int idlogin;
    int hn;
    String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        //mCon = getApplication();

        Bundle bundle = getIntent().getExtras();
        idlogin = bundle.getInt("idlogin");
        hn = bundle.getInt("hn");
        System.out.println("id (2) "+idlogin+" hn(2) "+hn);
        tvEmptyTextView = (TextView) findViewById(R.id.empty_view);
       // etv = (TextView) findViewById(R.id.empty_view2);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = new ArrayList<>();
        loadData();

        Button btnaddnew = (Button)findViewById(R.id.addnew);
        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewRoomseq();
            }
        });
    }


    public void loadData() {

        TextView txtime = (TextView) findViewById(R.id.txttime);
        txtime.setText(dateToday);

        Call<List<Data>> call = api.getRoomseqIdDate(idlogin,dateToday);
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                mDataSet = new ArrayList<>(response.body());

//                if(mDataSet.isEmpty()){
//                    mRecyclerView.setVisibility(View.GONE);
//                    tvEmptyTextView.setVisibility(View.VISIBLE);
//
//                }else{
//                    mRecyclerView.setVisibility(View.VISIBLE);
//                    tvEmptyTextView.setVisibility(View.GONE);
//                }

                SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(context, mDataSet);
                SwipeRecyclerViewAdapter setid = new SwipeRecyclerViewAdapter(idlogin);

                ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e("RecyclerView", "onScrollStateChanged");
                    }
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }


    public void addnewRoomseq(){
        Intent intent = new Intent(this,AddnewActivity.class);
        intent.putExtra("id",idlogin);
        intent.putExtra("hn",hn);
        startActivity(intent);
        CustomIntent.customType(this,"left-to-right");
    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this,"right-to-left");
    }

}
