package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListNoItem extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);

    private ArrayList<Data> itemlist = new ArrayList<>();
    private AdepterHislist adepterHislist;
    private RecyclerView recyclerView;

    private int idlogin;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis_no_item);
        Bundle b = getIntent().getExtras();
        idlogin = b.getInt("idlogin");
        date = b.getString("Msel");
        this.setTitle("ประวัติ "+date);
        System.out.println(idlogin);
        System.out.println(date);

        recyclerView = (RecyclerView)findViewById(R.id.recyhlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadlist();

    }
    private void loadlist(){
        Call<List<Data>> call = api.hislist(1,date);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                itemlist = new ArrayList<>(response.body());
                adepterHislist = new AdepterHislist(itemlist,ListNoItem.this);
                recyclerView.setAdapter(adepterHislist);
            }


            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }

        });


    }
}
