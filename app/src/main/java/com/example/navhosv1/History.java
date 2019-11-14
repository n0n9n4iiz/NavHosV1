package com.example.navhosv1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Spinner spinner_year;
    private Spinner spinner_mm;

    private  List<Datayear> data;
    private ArrayList<Datayear> datayear = new ArrayList<Datayear>();
    private ArrayAdapter<Datayear> adapteryear;

    private  List<Datamonth> datamonths;
    private ArrayList<Datamonth> datamm = new ArrayList<Datamonth>();
    private ArrayAdapter<Datamonth> adaptermm;

    private ArrayList<DataGroupMM> dataGroupMMS = new ArrayList<>();
    private AdapterRecGroupMM adapterRecGroupMM;
    private RecyclerView his_Recyview;

    private ArrayList<String> formatMM;

    private int idlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.histrory);
        Bundle b = getIntent().getExtras();
        idlogin = b.getInt("idlogin");
        spinner_year = (Spinner) findViewById(R.id.spinneryear);
        getYearToSpinnerAdapter();
        spinner_mm = (Spinner) findViewById(R.id.spinnermm);

        his_Recyview = (RecyclerView) findViewById(R.id.hisRecy);
        his_Recyview.setLayoutManager(new LinearLayoutManager(this));

        adapterRecGroupMM = new AdapterRecGroupMM(idlogin);

    }
    public void getYearToSpinnerAdapter(){
        adapteryear = new ArrayAdapter<Datayear>(this,android.R.layout.simple_dropdown_item_1line,datayear );

        Call<List<Datayear>> call = api.getYear(idlogin);
        call.enqueue(new Callback<List<Datayear>>() {
            @Override
            public void onResponse(Call<List<Datayear>> call, Response<List<Datayear>> response) {
                data = response.body();
                for(Datayear d : data){
                    datayear.add(new Datayear(d.getYear()));
                    spinner_year.setAdapter(adapteryear);
                }
            }
            @Override
            public void onFailure(Call<List<Datayear>> call, Throwable t) {
            }
        });
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final Datayear textyear = (Datayear) adapterView.getSelectedItem();
                System.out.println(textyear.getYear());

                final Context context = view.getContext();
                adaptermm = new ArrayAdapter<Datamonth>(context,android.R.layout.simple_dropdown_item_1line,datamm );
                adaptermm.clear();
                // spinnermm follow spineryear
                //add connect month later
                Call<List<Datamonth>> call = api.getMonth(idlogin,textyear.getYear());
                call.enqueue(new Callback<List<Datamonth>>() {

                    @Override
                    public void onResponse(Call<List<Datamonth>> call, Response<List<Datamonth>> response) {
                        datamonths = response.body();
                        for(Datamonth d : datamonths){
                            String formatmonth = "";
                            switch (d.getMonth()){
                                case "01" : formatmonth = "มกราคม"; break;
                                case "02" : formatmonth = "กุมภาพันธ์"; break;
                                case "03" : formatmonth = "มีนาคม"; break;
                                case "04" : formatmonth = "เมษายน"; break;
                                case "05" : formatmonth = "พฤษภาคม"; break;
                                case "06" : formatmonth = "มิถุนายน"; break;
                                case "07" : formatmonth = "กรกฎาคม"; break;
                                case "08" : formatmonth = "สิงหาคม"; break;
                                case "09" : formatmonth = "กันยายน"; break;
                                case "10" : formatmonth = "ตุลาคม"; break;
                                case "11" : formatmonth = "พฤศจิกายน"; break;
                                case "12" : formatmonth = "ธันวาคม"; break;
                            }
                            datamm.add(new Datamonth(d.getMonth(),formatmonth));
                            spinner_mm.setAdapter(adaptermm);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Datamonth>> call, Throwable t) {

                    }
                });

                spinner_mm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> adapterView, final View view, int i, long l) {
                        final Datamonth textmonth = (Datamonth) adapterView.getSelectedItem();
                        System.out.println(textmonth.getMonth());
                        final Context c2 = view.getContext();
                        Call<List<DataGroupMM>> call = api.getGroupMM(idlogin,textmonth.getMonth(),textyear.getYear());
                        call.enqueue(new Callback<List<DataGroupMM>>() {
                            @Override
                            public void onResponse(Call<List<DataGroupMM>> call, Response<List<DataGroupMM>> response) {
                                dataGroupMMS = new ArrayList<>(response.body());
                                for (DataGroupMM d : dataGroupMMS
                                     ) {
                                    System.out.println("aaa "+d.getDate());
                                }
                                adapterRecGroupMM = new AdapterRecGroupMM(c2,dataGroupMMS);
                                his_Recyview.setAdapter(adapterRecGroupMM);
                            }

                            @Override
                            public void onFailure(Call<List<DataGroupMM>> call, Throwable t) {

                            }
                        });


                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void finish(){
        super.finish();
        CustomIntent.customType(this,"right-to-left");
    }


}

