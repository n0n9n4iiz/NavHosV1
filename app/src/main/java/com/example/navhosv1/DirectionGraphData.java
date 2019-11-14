package com.example.navhosv1;
import java.io.Serializable;


public class DirectionGraphData implements Serializable {

    private static final long serialVersionUID = 1L;

    private double x;

    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public DirectionGraphData(double x, double y) {
        this.x = x;
        this.y = y;

    }
}