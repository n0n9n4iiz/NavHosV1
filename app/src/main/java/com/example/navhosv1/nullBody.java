package com.example.navhosv1;

public class nullBody {

    private String string;
    private int num;

    public nullBody(String string, int num) {
        this.string = string;
        this.num = num;
    }

    public nullBody(String string) {
        this.string = string;
    }

    public nullBody(int num) {
        this.num = num;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
