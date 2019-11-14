package com.example.navhosv1;

public class Datayear {
    //USE FOR SPINNER
    private String year;
    private String f_year;

    public Datayear(String year) {

        this.year = year;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year;
    }

}
