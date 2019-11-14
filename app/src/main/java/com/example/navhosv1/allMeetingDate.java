package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class allMeetingDate extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    ArrayList<Data> arrData = new ArrayList<>();
    int id;
    int hn;
    private  AdapterAllMeet adapterAllMeet;
    private RecyclerView recyclerView;
    private TextView Empty_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_meeting_date);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("idlogin");
        hn = bundle.getInt("hn");
        Empty_date = (TextView) findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_all_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllMeetDate();

    }

    private void getAllMeetDate() {
       Call<List<Data>> call = api.getAllMeetDate(id);
       call.enqueue(new Callback<List<Data>>() {
           @Override
           public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
               arrData = new ArrayList<>(response.body());
               if(arrData.isEmpty()){
                   recyclerView.setVisibility(View.GONE);
                   Empty_date.setVisibility(View.VISIBLE);

               }else{
                   recyclerView.setVisibility(View.VISIBLE);
                   Empty_date.setVisibility(View.GONE);
               }
               adapterAllMeet = new AdapterAllMeet(allMeetingDate.this,arrData,id,hn);
               recyclerView.setAdapter(adapterAllMeet);

           }

           @Override
           public void onFailure(Call<List<Data>> call, Throwable t) {

           }
       });
    }
}
