package com.example.navhosv1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFromList extends AppCompatActivity {


    //set api
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://obscure-refuge-47108.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PointService api = retrofit.create(PointService.class);
    MapService api2 = retrofit.create(MapService.class);
    DirectionGraph api3 = retrofit.create(DirectionGraph.class);



    //shortest
    private static final int NO_PARENT = -1;
    private TextView result;
    private TextView distanceV;

    int startPointID;
    private static int End;
    static double distance;
    static ArrayList<Integer> path = new ArrayList<Integer>();
    static ArrayList<Double> distanceAr = new ArrayList<Double>();
    //    private ArrayList<Integer> myudata = new ArrayList<Integer>();
    private ArrayList<String> newData = new ArrayList<String>();
    private ArrayList<String> newData2 = new ArrayList<String>();
    private ArrayList<String> roominfo = new ArrayList<String>();

    //drawline
    private Context mContext;
    private Resources mResources;
    private LinearLayout mRelativeLayout;
    private Button lostbutton,nextbutton;
    private TextView directionstep;
    private ImageView mImageView;

    //listspin
    private ArrayList<String> detailofPiont = new ArrayList<String>();

    private ArrayList<Double> xRatio = new ArrayList<Double>();
    private ArrayList<Double> yRatio = new ArrayList<Double>();
    private ArrayList<Double> xDG = new ArrayList<Double>();
    private ArrayList<Double> yDG = new ArrayList<Double>();
    private ArrayList<String> directAr = new ArrayList<String>();
    private ArrayList<String> directAr2 = new ArrayList<String>();
    private ArrayList<Double> distancefordirect = new ArrayList<Double>();

    double x1R, x2R, y1R, y2R;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_from_list);


        path.clear();
        newData.clear();
        newData2.clear();
        directAr.clear();
        directAr2.clear();
        distancefordirect.clear();


        getDetail();
        getDirectGraphInfo();

        Bundle bundle = getIntent().getExtras();
        System.out.println(bundle.getInt("START"));
        System.out.println(bundle.getInt("END"));
        startPointID=bundle.getInt("START");
        End=bundle.getInt("END");


        result = (TextView) findViewById(R.id.result);
        distanceV = (TextView) findViewById(R.id.distance_text);
        nextbutton = (Button)findViewById(R.id.nextbutton);
        lostbutton=(Button)findViewById(R.id.lostbutton);
        directionstep = (TextView) findViewById(R.id.directionstep);

        // Get the application context
        mContext = getApplicationContext();
        // Get the Resources
        mResources = getResources();
        // Get the widgets reference from XML layout
        mRelativeLayout = (LinearLayout) findViewById(R.id.rl);

        mImageView = (ImageView) findViewById(R.id.iv);
        mImageView.setImageResource(R.drawable.mapforuse);



    }

    public void shortest() {


        double[][] adjacencyMatrix = {
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0 },
                { 0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,12,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0,6,0,0,0,0,17,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,17,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,10,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,11,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,10,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,3,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,2,0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,12,0,6,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,6,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,11,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,5,0,8,0,0,0,0,0,0,16,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0,0,8,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,9,0,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,7,0,0,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,5,0,0,12,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,12,0,0,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0,10,0,0,0,0,6,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,16,0,0,0,12,0,0,10,0,6,0,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,7,0,0,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7,0,5,0,0,0,9 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,5,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,7,4,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6,0,0,0,0,0,7,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,9,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0 },
                { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,16,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,0,0,0,0,0 }
        };


        dijkstra(adjacencyMatrix, startPointID);

        //map คู้
        for (int i2 = 0; i2 < (path.size() - 1); i2++) {
            String datanew = "";
            try {
                datanew = "{" + path.get(i2) + "," + path.get(i2 + 1) + "}";
                newData.add(datanew);
            } catch (Exception e) {
//               System.out.println(e.toString());

            }
        }

        //for send to compare xratio
        for (int i2 = 0; i2 < (path.size() - 1); i2++) {
            try {

                newData2.add(path.get(i2).toString());
                newData2.add(path.get(i2 + 1).toString());
            } catch (Exception e) {
//                System.out.println(e.toString());
            }
        }

//        System.out.println(newData2);
//        System.out.println("size p " + newData2.size());

        StringBuilder sb = new StringBuilder();
        for (int i : path) {
            sb.append(Integer.toString(i) + " ");

        }
        String str = sb.toString();
        result.setText("path " + path + " ");

        String dis = "ระยะทางทั้งสิ้น" +Double.toString(distance);
        distanceV.setText("Disttance " + dis);
        directionstep.setText(dis);

        drawline();
//        getNameOfPointForDirection();
        nextbutton.setOnClickListener(new View.OnClickListener() {
            int j=0;
            @Override
            public void onClick(View view) {
                sendtextdirect(j);

                if(j>0&&newData2.size()>2){
                newData2.remove(0);
                newData2.remove(1);

                drawline();} if(newData2.size()<=2){
                    nextbutton.setVisibility(View.GONE);
                }
                j=j+1;


            }
        });
        lostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, SelectCurrentLocation.class);
                intent.putExtra("endfromMap",End);
                context.startActivity(intent);

            }
        });

    }

    private static void dijkstra(double[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        double[] shortestDistances = new double[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++) {
            shortestDistances[vertexIndex] = Double.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            double shortestDistance = Double.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = (shortestDistance +
                            edgeDistance);
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents, End);
    }

    private static void printSolution(int startVertex, double[] distances, int[] parents, int End) {
        int nVertices = distances.length;
        System.out.print("Vertex\t Distance\tPath");

//		for (int vertexIndex = 0;
//				vertexIndex < nVertices;
//				vertexIndex++)

        if (End != startVertex) {
            System.out.print("\n" + startVertex + " -> ");
            System.out.print(End + " \t\t ");
            System.out.print(distances[End] + "\t\t");

            distance = distances[End];
            printPath(End, parents);

//            for(int i = 0; i < distances.length;i++ ){
//                distanceAr.add(distances[i]);
//                System.out.print("distancetest "+distances[i] );
//            }

        }

    }

    private static void printPath(int currentVertex, int[] parents) {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        path.add(currentVertex);
        System.out.print(currentVertex + " ");

    }

    public void drawline() {

        if(newData2!=null) {
            //draw
            // Initialize a new Bitmap object
            Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.mapforuse);
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // Initialize a new Canvas instance
            Canvas canvas = new Canvas(bitmap);

            // Draw a solid color on the canvas as background
            // canvas.drawColor(Color.LTGRAY);

            // Initialize a new Paint instance to draw the line
            Paint paint = new Paint();
            // Line color
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);
            // Line width in pixels
            paint.setStrokeWidth(8);
            paint.setAntiAlias(true);

                /*
                    public void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
                        Draw a line segment with the specified start and stop x,y coordinates, using
                        the specified paint.

                        Note that since a line is always "framed", the Style is ignored in the paint.

                        Degenerate lines (length is 0) will not be drawn.

                    Parameters
                        startX : The x-coordinate of the start point of the line
                        startY : The y-coordinate of the start point of the line
                        paint : The paint used to draw the line

                */
            int startline, endline;

            System.out.println("newData2" + newData2);
            for (int i = 0; i < newData2.size(); i = i + 2) {
                startline = Integer.parseInt(newData2.get(i));
                endline = Integer.parseInt(newData2.get(i + 1));
                for (int j = 0; j < xRatio.size(); j++) {
                    x1R = xRatio.get(startline);
                    x2R = xRatio.get(endline);
                    y1R = yRatio.get(startline);
                    y2R = yRatio.get(endline);

                    double x = canvas.getWidth();
                    double y = canvas.getHeight();
//                System.out.println("x " + x + " y" + y);
                    double x1 = x1R * x;
                    double x2 = x2R * x;
                    double y1 = y1R * y;
                    double y2 = y2R * y;
//                System.out.println("x1 " + x1 + " y1" + y1);
                    int xint1 = (int) x1;
                    int xint2 = (int) x2;
                    int yint1 = (int) y1;
                    int yint2 = (int) y2;

                    // Draw a line on canvas at the center position
                    canvas.drawLine(
                            xint1, // startX
                            yint1, // startY
                            xint2, // stopX
                            yint2, // stopY
                            paint // Paint
                    );
                    Paint p1 = new Paint();
                    // Line color
                    p1.setColor(Color.RED);
                    p1.setStyle(Paint.Style.STROKE);
                    // Line width in pixels
                    p1.setStrokeWidth(25);
                    p1.setAntiAlias(true);
                    System.out.println("check i indraw line" + i);
//mark destination
                    if (i == newData2.size() - 2) {
                        canvas.drawPoint(xint2, yint2, p1);
                    }

                    Paint p2 = new Paint();
                    // Line color
                    p2.setColor(getResources().getColor(R.color.colorPath));
                    p2.setStyle(Paint.Style.STROKE);
                    // Line width in pixels
                    p2.setStrokeWidth(25);
                    p2.setAntiAlias(true);
                    if (i == 0) {
                        canvas.drawPoint(xint1, yint1, p2);
                    }
                }
            }
            // Display the newly created bitmap on app interface
            mImageView.setImageBitmap(bitmap);


            System.out.println("path " + path);
            calDirection();
            System.out.println("directar " + directAr);
            System.out.println("distancefordirect " + distancefordirect);
            addDirectToArr();
            System.out.println("ar2Dist = " + directAr2);
        }
        else{

        }
    }
    double xResult, yResult, xd1, xd2, yd1, yd2;

    public void calDirection() {
        for (int i = 0; i < path.size() - 1; i++) {

//                System.out.println( "path get i= "+ path.get(i));

            xd1 = xDG.get(path.get(i));

            xd2 = xDG.get(path.get(i + 1));
            yd1 = yDG.get(path.get(i));
            yd2 = yDG.get(path.get(i + 1));
            System.out.println("xd1=" + xd1 + "xd2=" + xd2);
            addDirectionToArr(xd1, xd2, yd1, yd2);


        }
    }

    public void addDirectionToArr(double x1, double x2, double y1, double y2) {
//System.out.println("Direct= x " + x1+" x "+x2+" y "+y1+" y "+y2);
        String d = "";
        double dis=0;
        if (x1 > x2) {
            d = "l";
            dis=x1-x2;
        } else if (x1 < x2) {
            d = "r";
            dis=x2-x1;
        } else if (y1 < y2) {
            d = "u";
            dis=y2-y1;
        } else if (y1 > y2) {
            d = "d";
            dis=y1-y2;
        }
        directAr.add(d);
        distancefordirect.add(dis);

    }

    public void addDirectToArr() {
        for (int i = 0; i < directAr.size(); i++) {
            if (i == 0) {
                directAr2.add("ตรง");

            } else {
                calDirectionFinal( directAr.get(i - 1),directAr.get(i));


            }

        }

    }

    public void deletePointnotChangeDirection(){
        for(int i=1;i<directAr2.size();i++){
            if(directAr2.get(i).equals("ตรง"))


            distanceAr.set(i-1,distanceAr.get(i-1)+distanceAr.get(i));
            distanceAr.remove(i);
            directAr2.remove(i);


        }
    }
    public void calDirectionFinal(String s1,String s2){
        if( (s1.equals("r") && s2.equals("r"))|(s1.equals("l") && s2.equals("l"))|(s1.equals("u") && s2.equals("u"))|(s1.equals("d") && s2.equals("d"))){
            directAr2.add("ตรง");

        }
        else if( (s1.equals("d") && s2.equals("r"))|(s1.equals("r") && s2.equals("u"))|(s1.equals("l") && s2.equals("d"))|(s1.equals("u") && s2.equals("l"))){
            directAr2.add("เลี้ยวซ้าย");
        }
        else if( (s1.equals("d") && s2.equals("l"))|(s1.equals("u") && s2.equals("r"))|(s1.equals("r") && s2.equals("d"))|(s1.equals("l") && s2.equals("u"))){
            directAr2.add("เลี้ยวขวา");
        }


    }

    public String printDirectionStep(String direction,double distance){
        String dis = Double.toString(distance);
        String text = direction+ "ไป"+distance + " m";
       return  text;
    }

    public void getNameOfPointForDirection(String start,String end){
        for (int i=0;i<newData2.size();i=i+2){
            start = newData2.get(i);
            String End = newData2.get(i+1);
            System.out.println("chect start direct"+start);
            System.out.println("chect start direct"+End);
        }

    }

    public void  sendtextdirect(int i){

        String text="";

        System.out.println("check i"+i);
        if(i>=directAr2.size()){
            nextbutton.setVisibility(View.GONE);
        }
        else{
            String x = directAr2.get(i);
            double d = distancefordirect.get(i);

            text =printDirectionStep(x,d);
            directionstep.setText(text);
            MyTTS.getInstance(this)
                    .setEngine("com.google.android.tts").setLocale(new Locale("th"))
                    .speak(text);


        }
    }

    public void getRatio() {

        Call<List<Mapdata>> call = api2.getMapInfo();
        call.enqueue(new Callback<List<Mapdata>>() {

            @Override
            public void onResponse(Call<List<Mapdata>> call, Response<List<Mapdata>> response) {
                List<Mapdata> data = response.body();
                for (Mapdata d : data) {
                    xRatio.add(d.getX_ratio());
                    yRatio.add(d.getY_ratio());
                }
                shortest();
            }

            @Override
            public void onFailure(Call<List<Mapdata>> call, Throwable t) {
                System.out.println("call failed");
            }
        });

    }

    public void getDirectGraphInfo() {

        Call<List<DirectionGraphData>> call = api3.getDirectionGraphInfo();
        call.enqueue(new Callback<List<DirectionGraphData>>() {

            @Override
            public void onResponse(Call<List<DirectionGraphData>> call, Response<List<DirectionGraphData>> response) {
                List<DirectionGraphData> data = response.body();
                for (DirectionGraphData d : data) {
                    xDG.add(d.getX());
                    yDG.add(d.getY());
//                    System.out.println("call DGSUCKSEED" + xDG + yDG);
                }
                getRatio();

            }

            @Override
            public void onFailure(Call<List<DirectionGraphData>> call, Throwable t) {
                System.out.println("call failed");
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
               detailofPiont.add(d.getP_detail_for_direction());
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


