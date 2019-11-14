package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingDateByDate extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    ArrayList<Data> arrItem;
    RecyclerView recyclerView;
    AdapterMeetingDateByDate adapterMeetingDateByDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_date_by_date);
        Bundle bundle = getIntent().getExtras();
        this.setTitle("รายการนัด "+bundle.getString("date"));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loaddata(bundle.getInt("id"),bundle.getString("date"));


    }
    public void  loaddata(int id,String date){
    Call<List<Data>> call = api.getMeetByall(id,date);
    call.enqueue(new Callback<List<Data>>() {
        @Override
        public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
            arrItem = new ArrayList<>(response.body());
            System.out.println(arrItem.size());
            adapterMeetingDateByDate = new AdapterMeetingDateByDate(MeetingDateByDate.this,arrItem);
           recyclerView.setAdapter(adapterMeetingDateByDate);
        }

        @Override
        public void onFailure(Call<List<Data>> call, Throwable t) {
            System.out.println(t);
        }
    });

    }
}
