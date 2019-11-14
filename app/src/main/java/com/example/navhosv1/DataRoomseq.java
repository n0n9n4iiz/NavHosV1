package com.example.navhosv1;
import com.example.navhosv1.SwipeRecyclerViewAdapter;
import com.google.gson.annotations.SerializedName;

public class DataRoomseq{

    static int hn;

    static String date;

    static String room;

    static int no;

  //  private String time;

    public DataRoomseq(){

    }

    public int getHn() {
        return hn;
    }

    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public int getNo() {
        return no;
    }

//    public String getTime() {
//        return time;
//    }

    public void setHn(int hn) {
        this.hn = hn;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setNo(int no) {
        this.no = no;
    }

//    public void setTime(String time) {
//        this.time = time;
//    }
}
