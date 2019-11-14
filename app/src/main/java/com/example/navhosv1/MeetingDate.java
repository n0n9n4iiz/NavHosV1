package com.example.navhosv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeetingDate extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);

    CalendarView calender;
    String fday;
    String fmonth;
    static String Date;
    int idlogin;
    int hn;
    private TextView etv;
    List<Data> data;
    static TextView todaycheck;
    String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    private RecyclerView RecyclerViewND;
    private ArrayList<Data> dataNextday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_date);
        Bundle bundle = getIntent().getExtras();
        idlogin = bundle.getInt("idlogin");
        hn = bundle.getInt("hn");
        calender = (CalendarView) findViewById(R.id.calendarall);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
          @Override
           public void onSelectedDayChange(
          @NonNull
           CalendarView view, int year, int month, int dayOfMonth)
                {
                    dataNextday.clear();

                    month = month +1;
                    if(dayOfMonth == 1 || dayOfMonth == 2 || dayOfMonth == 3 || dayOfMonth == 4 || dayOfMonth == 5 ||
                            dayOfMonth == 6 || dayOfMonth == 7 || dayOfMonth == 8 || dayOfMonth == 9 ){
                         fday = "0"+dayOfMonth;
                    }else{
                        fday = String.valueOf(dayOfMonth);
                    }
                    if(month == 1 || month == 2 || month == 3 || month == 4 || month == 5 ||
                            month == 6 || month == 7 || month == 8 || month == 9 ){
                        fmonth = ("0"+month);
                    }else{
                        fmonth = String.valueOf(month);
                    }

            Date = fday + "/" + (fmonth) + "/" + year;
                    loadDataNextday();
                }
          });


        etv = (TextView) findViewById(R.id.empty_view2);
        RecyclerViewND = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerViewND.setLayoutManager(new LinearLayoutManager(this));
        dataNextday = new ArrayList<>();
        dataNextday.clear();

        loadDataNextday();

        Button btnnext = (Button) findViewById(R.id.btn_next);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MeetingDate.this,allMeetingDate.class);
                i.putExtra("idlogin",idlogin);
                i.putExtra("hn",hn);
                startActivity(i);
                CustomIntent.customType(MeetingDate.this,"left-to-right");
            }
        });
    }


    public void loadDataNextday(){
        todaycheck = (TextView) findViewById(R.id.empty_view2);

        if(Date==null){
            Date=dateToday;
        }

        System.out.println(Date);

        Call<List<Data>> call = api.getDateMeet(idlogin,Date);
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                 data = response.body();
                System.out.println("d "+data.isEmpty());
                if(data.isEmpty()){
                    todaycheck.setText("ไม่พบรายการนัด");
                }
                for(Data d : data){
//                    if(Integer.parseInt(Date.substring(0,1))>Integer.parseInt(dateToday.substring(0,1))){
//                        dataNextday.add(new Data(d.getRoom(),d.getDate(),d.getNo(),d.getHn()));
//                    }
                    if(Integer.parseInt(Date.substring(6,10))==Integer.parseInt(dateToday.substring(6,10))){
                        System.out.println("A");
                        if(Integer.parseInt(Date.substring(3,5))>Integer.parseInt(dateToday.substring(3,5))){
                            System.out.println("B");
                            dataNextday.add(new Data(d.getRoom(),d.getDate(),d.getNo(),d.getHn(),d.getSuccess()));
                        }else if(Integer.parseInt(Date.substring(3,5))==Integer.parseInt(dateToday.substring(3,5))){
                            System.out.println("C");
                            if(Integer.parseInt(Date.substring(0,2))>Integer.parseInt(dateToday.substring(0,2))){
                                System.out.println("D");
                                dataNextday.add(new Data(d.getRoom(),d.getDate(),d.getNo(),d.getHn(),d.getSuccess()));
                            }
                            else if(Integer.parseInt(Date.substring(0,2))==Integer.parseInt(dateToday.substring(0,2))){
                                System.out.println("E");
                                todaycheck.setText("กรุณาตรวจสอบที่รายการของฉัน");
                                System.out.println("gotit");
                            }
                        }
                    }else if(Integer.parseInt(Date.substring(6,10))>Integer.parseInt(dateToday.substring(6,10))){
                        System.out.println("F");
                        dataNextday.add(new Data(d.getRoom(),d.getDate(),d.getNo(),d.getHn(),d.getSuccess()));
                    }



                }

                if(dataNextday.isEmpty()){
                    RecyclerViewND.setVisibility(View.GONE);
                    etv.setVisibility(View.VISIBLE);

                }else{
                    RecyclerViewND.setVisibility(View.VISIBLE);
                    etv.setVisibility(View.GONE);
                }

                AdapterMeeting adpt = new AdapterMeeting(this, dataNextday);

                ((AdapterMeeting) adpt).setMode(Attributes.Mode.Single);

                RecyclerViewND.setAdapter(adpt);

                RecyclerViewND.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    public  void finish(){
        super.finish();
        dataNextday.clear();
        data.clear();
        Date = null;
    }


}



