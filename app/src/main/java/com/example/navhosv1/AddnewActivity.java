package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import retrofit2.http.Header;

public class AddnewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    static String room;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://obscure-refuge-47108.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api2 = retrofit2.create(API.class);
    private int id;
    private int hn;
    Context context;
    private String dateToday;
    private ArrayList<Data> arrdatanew = new ArrayList<>();
    MyActivity myActivity = new MyActivity();
    SwipeRecyclerViewAdapter s;
    private RecyclerView rc;
    //spinner
    private Spinner spiner;
    private ArrayList<DataRoom> arrnameroom = new ArrayList<>();
    private ArrayAdapter<DataRoom> adapnameroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew_activity);
        Bundle b = getIntent().getExtras();
        rc = (RecyclerView) findViewById(R.id.my_recycler_view);
        id = b.getInt("id");
        hn = b.getInt("hn");
        dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        final EditText btn_datepicker = (EditText) findViewById(R.id.datesel);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---datepicker
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        spiner = (Spinner)findViewById(R.id.spinroom);
        getListRoom();

        Button btn = (Button) findViewById(R.id.adddata);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Context ct = view.getContext();
                DataRoomseq dr = new DataRoomseq();
                dr.setDate(btn_datepicker.getText().toString()+"");
                dr.setHn(hn);
                dr.setRoom(room);
                if (dr.getRoom() == "-- เลือกสถานที่ --" && dr.getDate() == "") {
                    Toast.makeText(view.getContext(),"กรุณาเลือกวันที่และสถานที",Toast.LENGTH_SHORT).show();
                }else if(dr.getDate() == ""){
                    Toast.makeText(view.getContext(),"กรุณาเลือกวันที่",Toast.LENGTH_SHORT).show();
                }else if(dr.getRoom() == "-- เลือกสถานที่ --"){
                    Toast.makeText(view.getContext(),"กรุณาเลือกสถานที่",Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(dr.getDate().substring(6,10)) >= Integer.parseInt(dateToday.substring(6,10))){
                    if(Integer.parseInt(dr.getDate().substring(3,5)) < Integer.parseInt(dateToday.substring(3,5))){
                        Toast.makeText(view.getContext(),"กรุณาเลือกวันที่ใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                    }else if(Integer.parseInt(dr.getDate().substring(3,5)) >= Integer.parseInt(dateToday.substring(3,5))){
                        if(Integer.parseInt(dr.getDate().substring(0,2)) < Integer.parseInt(dateToday.substring(0,2))){
                            Toast.makeText(view.getContext(),"กรุณาเลือกวันที่ใหม่อีกครั้ง",Toast.LENGTH_SHORT).show();
                        }else if(Integer.parseInt(dr.getDate().substring(0,2)) >= Integer.parseInt(dateToday.substring(0,2))){
                            PostDataRoomseq pdr = new PostDataRoomseq(dr.getHn(), dr.getDate(), dr.getRoom());
                            Call<PostDataRoomseq> call = api.insertNewroom(pdr);
                            call.enqueue(new Callback<PostDataRoomseq>() {
                                @Override
                                public void onResponse(Call<PostDataRoomseq> call, Response<PostDataRoomseq> response) {
                                    if(!response.isSuccessful()){

                                    }
                                    Toast.makeText(view.getContext(),"Success!!",Toast.LENGTH_SHORT).show();

                                    loadnew();

                                }

                                @Override
                                public void onFailure(Call<PostDataRoomseq> call, Throwable t) {
                                    t.printStackTrace();

                                }
                            });
                        }
                    }
                }
//                else{
//
//                PostDataRoomseq pdr = new PostDataRoomseq(dr.getHn(), dr.getDate(), dr.getRoom());
//                Call<PostDataRoomseq> call = api.insertNewroom(pdr);
//                call.enqueue(new Callback<PostDataRoomseq>() {
//                    @Override
//                    public void onResponse(Call<PostDataRoomseq> call, Response<PostDataRoomseq> response) {
//                        if(!response.isSuccessful()){
//
//                        }
//                        Toast.makeText(view.getContext(),"Success!!",Toast.LENGTH_SHORT).show();
//
//                        loadnew();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<PostDataRoomseq> call, Throwable t) {
//                       t.printStackTrace();
//
//                    }
//                });
//            }

            }

        });


    }

    public void getListRoom() {

       Call<List<DataRoom>> call2 = api2.getNameRoom();
       call2.enqueue(new Callback<List<DataRoom>>() {
           @Override
           public void onResponse(Call<List<DataRoom>> call, Response<List<DataRoom>> response) {
               arrnameroom = new ArrayList<DataRoom>(response.body());
               adapnameroom = new ArrayAdapter<DataRoom>(AddnewActivity.this,android.R.layout.simple_dropdown_item_1line,arrnameroom);
               spiner.setAdapter(adapnameroom);

           }

           @Override
           public void onFailure(Call<List<DataRoom>> call, Throwable t) {

           }
       });
       spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               room = adapterView.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);

        String currentDateString = (android.text.format.DateFormat.format("dd/MM/yyyy", c.getTime())).toString();

        System.out.println(currentDateString);
        EditText bdate = (EditText) findViewById(R.id.datesel);
        bdate.setText(currentDateString);
    }
public  void loadnew(){
    Call<List<Data>> call = api.getRoomseqIdDate(id,dateToday);
    call.enqueue(new Callback<List<Data>>() {
        @Override
        public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
            arrdatanew = new ArrayList<>(response.body());
            for (Data d: arrdatanew) {
                if(d.getNo() == (arrdatanew.size())){
                    System.out.println("New");
                    System.out.println("No "+d.getNo()+"room "+d.getRoom());
                }else {
                    System.out.println("No " + d.getNo() + "room " + d.getRoom());
                }
            }
            s = new SwipeRecyclerViewAdapter(context,arrdatanew);
            MyActivity.mRecyclerView.setAdapter(s);

            finish();

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
