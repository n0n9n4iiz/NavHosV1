package com.example.navhosv1;

public class Datamonth {

    private String month;
    private String fmonth;

    public Datamonth(String month ,String fmonth) {
        this.month = month;
        this.fmonth = fmonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFmonth() {
        return fmonth;
    }

    public void setFmonth(String fmonth) {
        this.fmonth = fmonth;
    }

    @Override
    public String toString() {
        return fmonth;
    }
}
