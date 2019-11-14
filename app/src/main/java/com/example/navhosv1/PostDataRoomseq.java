package com.example.navhosv1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class PostDataRoomseq {

    private int hn;

    private String date;

    private String room;



   // private String time;

    public PostDataRoomseq(int hn, String date, String room) {
        this.hn = hn;
        this.date = date;
        this.room = room;

       // this.time = time;
    }
}
