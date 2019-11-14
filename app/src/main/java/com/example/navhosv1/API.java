package com.example.navhosv1;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("allperson")
    Call<List<Data>> getData();

    @GET("getLogin/")
    Call<List<DataLogin>> getLogin(@Query("id") int id,
                                   @Query("bdate") String bdate);

    @GET("getperson/{id}")
    Call<List<Data>> getSelectId(@Path("id") int getPersonid);

    @GET("historybydate/{id}")
    Call<List<Data>> getHisByDate(@Path("id") int getPersonid);

    @GET("historyItem/")
    Call<List<Data>> getHisItem(@Query("id") int getPersonid,
                                @Query("date") String getDate);

    @GET("roomseq")
    Call<List<Data>> getRoomseqIdDate(@Query("id") int roomseqId,
                                  @Query("date") String roomseqDate );

    @DELETE("roomseq/deleteRoomseq/")
    Call<List<Data>> deleteRoomNoByHnDate(@Query("hn") int delByhn,
                                      @Query("date") String delBydate ,
                                      @Query("no") int delByno );
    @POST("/roomseq/addnew/")
    Call<PostDataRoomseq> insertNewroom(@Body PostDataRoomseq pdr);

    @GET("/nextday/roomseq/")
    Call<List<Data>> getRoomseqIdDateNextday(@Query("id") int roomseqId,
                                      @Query("date") String roomseqDate );

    @GET("/getDateMeet/")
    Call<List<Data>> getDateMeet(@Query("id") int id,
                                 @Query("date") String date);

    @PATCH("/getSuccess/")
     Call<Data> getSuccess(@Query("hn") int hn,
                          @Query("no") int no,
                          @Query("date") String date,@Body Data success);

    @PATCH("/updateDelete/")
    Call<Data> updateDel(@Query("hn") int hn,
                          @Query("date") String date,
                         @Body nullBody nullbody);

    @GET("/allMeetDate/")
    Call<List<Data>> getAllMeetDate(@Query("id") int id);

    @GET("/MeetDateByAll/")
    Call<List<Data>> getMeetByall(@Query("id") int id,
                                  @Query("date") String date);

    @GET("hisyear")
    Call<List<Datayear>> getYear(@Query("id") int id);

    @GET("hismonth/")
    Call<List<Datamonth>> getMonth(@Query("id") int id,
                                   @Query("year") String year);

    @GET("hisgroupmoth/")
    Call<List<DataGroupMM>> getGroupMM(@Query("id") int id,
                                       @Query("month") String month,
                                       @Query("year") String year);

    @GET("hislist/")
    Call<List<Data>> hislist(@Query("id") int id,
                             @Query("date") String date);

    @GET("room/1")
    Call<List<DataRoom>> getNameRoom();

    @GET("gethn/{id}")
    Call<List<Data>> getHN(@Path("id") int id);

    @DELETE("delGroupMeetDate/")
    Call<List<Data>> delGmeet(@Query("hn") int delByhn,
                              @Query("date") String delBydate);
}
