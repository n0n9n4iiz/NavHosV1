package com.example.navhosv1;

public class ItemHistory {
    private int no;
    private String room;
    private String date;

    public ItemHistory(int no, String room, String date) {
        this.no = no;
        this.room = room;
        this.date = date;
    }

    public int getNo() {
        return no;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }
}
