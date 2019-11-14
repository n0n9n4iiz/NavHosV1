package com.example.navhosv1;
import java.io.Serializable;


public class Mapdata implements Serializable {
    private static final long serialVersionUID = 1L;

    private int m_id;

    private int p_id;



    private int x_coor;

    private int y_coor;

    private int x_max;

    private int  y_max;

    private double x_ratio;

    private double y_ratio;

    public int getP_id() {
        return p_id;
    }

    public Mapdata(int m_id, int p_id, int x_coor, int y_coor, int x_max, int y_max, double x_ratio, double y_ratio){
      this.m_id=m_id;
      this.p_id=p_id;
      this.x_coor=x_coor;
      this.y_coor=y_coor;
      this.x_max=x_max;
      this.y_max=y_max;
      this.x_ratio=x_ratio;
      this.y_ratio=y_ratio;
    }

    public int getM_id() {
        return m_id;
    }

    public int getX_coor() {
        return x_coor;
    }

    public int getY_coor() {
        return y_coor;
    }

    public int getX_max() {
        return x_max;
    }

    public int getY_max() {
        return y_max;
    }

    public double getX_ratio() {
        return x_ratio;
    }

    public double getY_ratio() {
        return y_ratio;
    }
}
