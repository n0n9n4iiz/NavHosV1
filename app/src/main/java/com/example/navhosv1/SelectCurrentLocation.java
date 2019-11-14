package com.example.navhosv1;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelectCurrentLocation extends AppCompatActivity {

    private ArrayList<String> detailofPiont = new ArrayList<String>();
    //set api
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://obscure-refuge-47108.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PointService api = retrofit.create(PointService.class);

    //scan_btn
    private Button scan_btn,confirm_btn;

    //listspin
    private ArrayList<String> listSpin = new ArrayList<String>();
    private ArrayList<Integer> idofListSpin = new ArrayList<Integer>();
    private Spinner curentLocationSpinner;

    int startPointID;
    int End;
    String nameRoomEndFromList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_current_location);
        curentLocationSpinner = (Spinner) findViewById(R.id.currentlocationSpin);
//getDetail();
        //QR
        scan_btn = (Button) findViewById(R.id.scanqrBtn);
        confirm_btn=(Button) findViewById(R.id.confirmBtn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanQR();
            }
        });
        loadSpiinerData();
        Bundle bundle = getIntent().getExtras();
//        System.out.println(bundle.getString("room"));
        if(bundle.getString("room")!=null) {
            nameRoomEndFromList = bundle.getString("room");
            getIdFromEndSpinner(nameRoomEndFromList);
        }
        else {
            End=bundle.getInt("endfromMap");
        }








        curentLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SelectCurrentLocation.this,
                        "Select : " + curentLocationSpinner.getSelectedItem(),
                        Toast.LENGTH_SHORT).show();
                String y = curentLocationSpinner.getSelectedItem().toString();
                getIdFromStartSpinner(y);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCurrentLocation.this, MapFromList.class);
                intent.putExtra("START",startPointID  );
                intent.putExtra("END",End  );
                startActivity(intent);

            }
        });;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                startPointID = Integer.parseInt(result.getContents());



                Intent intent = new Intent(SelectCurrentLocation.this, MapFromList.class);
                intent.putExtra("START",startPointID  );
                intent.putExtra("END",End  );
                startActivity(intent);

//                for (int i =0;i<listSpin.size();i++) {
//                    if(idofListSpin.get(i).equals(startPointID)){
//
//                        curentLocationSpinner.setSelection(idofListSpin.indexOf(i));
//                        System.out.println("check qr "+ idofListSpin.indexOf(i));
//                    }
//                    else{
//
//                    }
////                spinner1.setSelection(11);
//                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //start scan
    final Activity activity = this;
    public void startScanQR() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    public void loadSpiinerData() {

        Call<List<Demo.Contributor>> call = api.getRoom("1");
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("Roomapi" + d.p_name + "jaaa");
                    listSpin.add(d.p_name);
                    idofListSpin.add(d.p_id);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, listSpin);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    curentLocationSpinner.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }

        });
    }
    public void getIdFromStartSpinner(String p_name) {

        Call<List<Demo.Contributor>> call = api.getPointID(p_name);
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("idapi" + d.p_id + "jaaa");

                    startPointID = d.p_id;
                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }
        });

    }

    public void getIdFromEndSpinner(String p_name) {

        Call<List<Demo.Contributor>> call = api.getPointID(p_name);
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    System.out.println("idapi" + d.p_id + "jaaa");

                    End = d.p_id;
                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call detail fffffffffffffffffffffffffffffailed");
            }
        });

    }

    public void getDetail(){
        Call<List<Demo.Contributor>> call = api.getPointInfo();
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();
                for (Demo.Contributor d : data) {
                    detailofPiont.add(d.p_detail_for_direction);
                    System.out.println("call detail ssssssssssssssssss");

                }
            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }

        });
    }
}
