package com.example.navhosv1;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    private int personid;

    private String firstname;

    private String lastname;

    private int hn;

    private String date;

    private String room;

    private int no;

    private String success;

    public Data(String room,String date,int no,int hn,String success){
        this.room = room;
        this.date = date;
        this.no = no;
        this.hn = hn;
        this.success = success;
    }
    public Data(String firstname,String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Data(String success){
        this.success = success;
    }

    public int getPersonid() {
        return personid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
