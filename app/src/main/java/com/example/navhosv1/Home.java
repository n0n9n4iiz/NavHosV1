package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    int idlogin;
    int hn;
    ArrayList<Data> arrHN = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        Bundle bundle = getIntent().getExtras();
        idlogin = bundle.getInt("idlogin");
        checkHN();
        System.out.println("id "+idlogin);
        Button btn_map = (Button) findViewById(R.id.button);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent i = new Intent(view.getContext(),MyMap.class);
                        startActivity(i);
            }
        });
        Button btn_myac = (Button) findViewById(R.id.button2);
        btn_myac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);
                myActivity();
            }
        });

        Button btnHis = (Button) findViewById(R.id.button3);
        btnHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animScale);
                myHistory();
            }
        });

        Button btnMeet = (Button) findViewById(R.id.button4);
                btnMeet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        meetingDate();
                    }
                });
    }

    public void myActivity(){

        Intent i = new Intent(this,MyActivity.class);
        i.putExtra("idlogin",idlogin);
        i.putExtra("hn",hn);
        startActivity(i);
        CustomIntent.customType(this,"left-to-right");

    }

    public void myHistory(){
        Intent i = new Intent(this, History.class);
        i.putExtra("idlogin",idlogin);
        startActivity(i);
        CustomIntent.customType(this,"left-to-right");

    }

    public void meetingDate(){
        Intent i = new Intent(this, MeetingDate.class);
        i.putExtra("idlogin",idlogin);
        i.putExtra("hn",hn);
        startActivity(i);
        CustomIntent.customType(this,"left-to-right");

    }

    public void checkHN(){
        Call<List<Data>> call = api.getHN(idlogin);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
               arrHN = new ArrayList<>(response.body());
                for (Data d : arrHN) {
                    hn = d.getHn();
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });

    }

    public void finish(){
        super.finish();
        CustomIntent.customType(this,"right-to-left");
    }
}
