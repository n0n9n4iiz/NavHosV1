package com.example.navhosv1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PointService {

        @GET("point")
        Call<List<Demo.Contributor>> getPointInfo();




        @GET("point/{name}")
        Call<List<Demo.Contributor>> getPointID(
                @Path("name") String name);


        @GET("room/{isRoom}")
        Call<List<Demo.Contributor>> getRoom(
                @Path("isRoom") String isRoom

        );



}
