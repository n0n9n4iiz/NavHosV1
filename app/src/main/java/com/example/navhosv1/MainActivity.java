package com.example.navhosv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Retrofit;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    private Button btn_login;
    static String login ="";
    private int idlogin;
    private int idc_int;
    private String check;
    private String currentDateString;
    private ArrayList<DataLogin> dataLogins = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

        //--login
            btn_login = (Button) findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(animScale);
                    login();

                }
            });
        EditText btn_datepicker = (EditText) findViewById(R.id.bdate);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---datepicker
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        TextView txt_gen = (TextView) findViewById(R.id.general);
        txt_gen.setPaintFlags(txt_gen.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        txt_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),NormalUser.class);
                i.putExtra("status","0");
                startActivity(i);
            }
        });

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,day);

         currentDateString = (android.text.format.DateFormat.format("dd/MM/yyyy", c.getTime())).toString();
        EditText bdate = (EditText) findViewById(R.id.bdate);
        bdate.setText(currentDateString);
    }

    public void login(){
         EditText idc = (EditText) findViewById(R.id.idc);
         check = idc.getText().toString();

             try {
                 idc_int = Integer.parseInt(check);
             }catch (Exception ex){

             }

        idlogin = idc_int;

Call<List<DataLogin>> call = api.getLogin(idlogin,currentDateString);
call.enqueue(new Callback<List<DataLogin>>() {
    @Override
    public void onResponse(Call<List<DataLogin>> call, Response<List<DataLogin>> response) {
        dataLogins = new ArrayList<>(response.body());
        for (DataLogin d : dataLogins) {
            System.out.println(d.getPersonid()+" , "+d.getBirthdate()+" , "+d.getFirstname());
            login = d.getPersonid();
        }
        nextActivity();
    }

    @Override
    public void onFailure(Call<List<DataLogin>> call, Throwable t) {

    }
});

    }
    public void nextActivity(){
        System.out.println(login);
        if(login.equals("0")){
            openDialog();
        }else {

            Intent i = new Intent(this, Home.class);
            i.putExtra("idlogin",idlogin);
            startActivity(i);
            CustomIntent.customType(this,"left-to-right");
        }

    }

    public void openDialog(){
        DialogErrLogin errLogin = new DialogErrLogin();
        errLogin.show(getSupportFragmentManager(),"DialogErrLogin");
    }

}
