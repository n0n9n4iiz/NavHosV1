package com.example.navhosv1;

public class DataRoom {
    private String p_name;

    public DataRoom(String p_name) {
        this.p_name = p_name;
    }

    public DataRoom() {
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    @Override
    public String toString() {
        return p_name;
    }
}
