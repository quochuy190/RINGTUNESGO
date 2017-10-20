package com.neo.media.ApiService;

import com.neo.media.Config.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by QQ on 7/4/2017.
 */

public interface ApiSeviceImpl {
    //Log info action user
    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> getservice(@Query("Service") String Service,
                                  @Query("Provider") String Provider,
                                  @Query("ParamSize") String ParamSize,
                                  @Query("P1") String P1,
                                  @Query("P2") String P2,
                                  @Query("P3") String P3,
                                  @Query("P4") String P4,
                                  @Query("P5") String P5);

    // get Album
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getAlbum(@Query("Service") String Service,
                                @Query("Provider") String Provider,
                                @Query("ParamSize") String ParamSize,
                                @Query("P1") String P1,
                                @Query("P2") String P2,
                                @Query("P3") String P3);

    //get Comment
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getComment(@Query("Service") String Service,
                                  @Query("Provider") String Provider,
                                  @Query("ParamSize") String ParamSize,
                                  @Query("P1") String P1,
                                  @Query("P2") String P2,
                                  @Query("P3") String P3,
                                  @Query("P4") String P4,
                                  @Query("P5") String P5,
                                  @Query("P5") String P6);

    //get Ringtunes by Topic
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getRingtunesByTopic(@Query("Service") String Service,
                                           @Query("Provider") String Provider,
                                           @Query("ParamSize") String ParamSize,
                                           @Query("P1") String P1,
                                           @Query("P2") String P2,
                                           @Query("P3") String P3,
                                           @Query("P4") String P4);

    //get Ringtunes new or hot
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getAllRingtunesHot(@Query("Service") String Service,
                                          @Query("Provider") String Provider,
                                          @Query("ParamSize") String ParamSize,
                                          @Query("P1") String P1,
                                          @Query("P2") String P2,
                                          @Query("P3") String P3,
                                          @Query("P4") String P4);

    // call ringtunes by album
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getRingtun_ByAlbum(@Query("Service") String Service,
                                          @Query("Provider") String Provider,
                                          @Query("ParamSize") String ParamSize,
                                          @Query("P1") String P1,
                                          @Query("P2") String P2,
                                          @Query("P3") String P3,
                                          @Query("P4") String P4,
                                          @Query("P5") String P5);

    //Api Search
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> search_Ringtunes(@Query("Service") String Service,
                                        @Query("Provider") String Provider,
                                        @Query("ParamSize") String ParamSize,
                                        @Query("P1") String P1,
                                        @Query("P2") String P2,
                                        @Query("P3") String P3,
                                        @Query("P4") String P4);

    //get All Singer
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getAllSinger(@Query("Service") String Service,
                                    @Query("Provider") String Provider,
                                    @Query("ParamSize") String ParamSize,
                                    @Query("P1") String P1,
                                    @Query("P2") String P2,
                                    @Query("P3") String P3);

    // get Detail Singer
    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> getDetailSinger(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P1") String P2

    );

    //add Group
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> addGroup(@Query("Service") String Service,
                                @Query("Provider") String Provider,
                                @Query("ParamSize") String ParamSize,
                                @Query("P1") String P1,
                                @Query("P2") String P2,
                                @Query("P3") String P3,
                                @Query("P4") String P4
    );

    //get all group
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> getAllGroup(@Query("Service") String Service,
                                   @Query("Provider") String Provider,
                                   @Query("ParamSize") String ParamSize,
                                   @Query("P1") String sesionID,
                                   @Query("P2") String msisdn,
                                   @Query("P3") String groupID
    );

    //get delete group
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> deleteGroup(@Query("Service") String Service,
                                   @Query("Provider") String Provider,
                                   @Query("ParamSize") String ParamSize,
                                   @Query("P1") String sesionID,
                                   @Query("P2") String msisdn,
                                   @Query("P3") String groupID
    );

    // add cli to group by group id
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> add_cli_to_group(@Query("Service") String Service,
                                        @Query("Provider") String Provider,
                                        @Query("ParamSize") String ParamSize,
                                        @Query("P1") String sesionID,
                                        @Query("P2") String msisdn,
                                        @Query("P3") String groupID,
                                        @Query("P4") String caller_msisdn
    );

    // add cli to group by group id
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> delete_cli_to_group(@Query("Service") String Service,
                                           @Query("Provider") String Provider,
                                           @Query("ParamSize") String ParamSize,
                                           @Query("P1") String sesionID,
                                           @Query("P2") String msisdn,
                                           @Query("P3") String groupID,
                                           @Query("P4") String caller_msisdn
    );

    // add cli to group by group id
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> delete_cli_to_mylist(@Query("Service") String Service,
                                            @Query("Provider") String Provider,
                                            @Query("ParamSize") String ParamSize,
                                            @Query("P1") String sesionID,
                                            @Query("P2") String msisdn,
                                            @Query("P3") String groupID,
                                            @Query("P4") String option);

    // get all collection
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> getAllConllection(@Query("Service") String Service,
                                         @Query("Provider") String Provider,
                                         @Query("ParamSize") String ParamSize,
                                         @Query("P1") String P1,
                                         @Query("P2") String P2
    );

    // thêm luật phát nhạc
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> add_Profiles(@Query("Service") String Service,
                                    @Query("Provider") String Provider,
                                    @Query("ParamSize") String ParamSize,
                                    @Query("P1") String P1,
                                    @Query("P2") String P2,
                                    @Query("P3") String P3,
                                    @Query("P4") String P4,
                                    @Query("P5") String P5,
                                    @Query("P6") String P6,
                                    @Query("P7") String P7
    );

    // update luật phát nhạc
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> update_Profiles(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P2") String P2,
                                       @Query("P3") String P3,
                                       @Query("P4") String P4,
                                       @Query("P5") String P5,
                                       @Query("P6") String P6,
                                       @Query("P7") String P7,
                                       @Query("P8") String P8
    );

    //get Content item -- detail songs -- thong tin chi tiet bai hat co han su dung
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> getContentItem(@Query("Service") String Service,
                                      @Query("Provider") String Provider,
                                      @Query("ParamSize") String ParamSize,
                                      @Query("P1") String P1,
                                      @Query("P2") String P2,
                                      @Query("P3") String P3
    );

    // add item to my list
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> addItemtoMyList(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P2") String P2,
                                       @Query("P3") String P3,
                                       @Query("P4") String P4
    );

    //3.20	ADD_GIFT_TO_PLAYLIST/tặng bài hát
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> addGifttoPlayList(@Query("Service") String Service,
                                         @Query("Provider") String Provider,
                                         @Query("ParamSize") String ParamSize,
                                         @Query("P1") String P1,
                                         @Query("P2") String P2,
                                         @Query("P3") String P3,
                                         @Query("P4") String P4,
                                         @Query("P5") String P5
    );

    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> addComment(@Query("Service") String Service,
                                  @Query("Provider") String Provider,
                                  @Query("ParamSize") String ParamSize,
                                  @Query("P1") String P1,
                                  @Query("P2") String P2,
                                  @Query("P3") String P3,
                                  @Query("P4") String P4,
                                  @Query("P5") String P5,
                                  @Query("P6") String P6,
                                  @Query("P7") String P7,
                                  @Query("P8") String P8,
                                  @Query("P9") String P9,
                                  @Query("P10") String P10,
                                  @Query("P11") String P11,
                                  @Query("P12") String P12
    );

    //3.10	GET_SUBSCRIBER_DETAILS/lấy thông tin thuê bao
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> get_SUBSCRIBER_DETAILS(@Query("Service") String Service,
                                              @Query("Provider") String Provider,
                                              @Query("ParamSize") String ParamSize,
                                              @Query("P1") String P1,
                                              @Query("P2") String P2
    );

    // USER_INIT/init thông tin user theo sdt
    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> init_service(@Query("Service") String Service,
                                    @Query("Provider") String Provider,
                                    @Query("ParamSize") String ParamSize,
                                    @Query("P1") String P1,
                                    @Query("P2") String P2,
                                    @Query("P3") String P3,
                                    @Query("P4") String P4,
                                    @Query("P5") String P5
    );

    // USER_INIT/init thông tin user theo sdt
    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P4_CRBT(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P2") String P2,
                                       @Query("P3") String P3,
                                       @Query("P4") String P4
    );

    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P2_CRBT(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P2") String P2
    );

    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P2_sql(@Query("Service") String Service,
                                      @Query("Provider") String Provider,
                                      @Query("ParamSize") String ParamSize,
                                      @Query("P1") String P1,
                                      @Query("P2") String P2
    );

    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> api_get_P2_sql_value(@Query("Service") String Service,
                                            @Query("Provider") String Provider,
                                            @Query("ParamSize") String ParamSize,
                                            @Query("P1") String P1,
                                            @Query("P2") String P2
    );

    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P1_sql(@Query("Service") String Service,
                                      @Query("Provider") String Provider,
                                      @Query("ParamSize") String ParamSize,
                                      @Query("P1") String P1

    );

    @GET("services/RbtServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P3_CRBT(@Query("Service") String Service,
                                       @Query("Provider") String Provider,
                                       @Query("ParamSize") String ParamSize,
                                       @Query("P1") String P1,
                                       @Query("P2") String P2,
                                       @Query("P3") String P3
    );

    @GET("services/SqlServices/ref?response=application/json&")
    Call<ResponseBody> api_get_P3_SQL(@Query("Service") String Service,
                                      @Query("Provider") String Provider,
                                      @Query("ParamSize") String ParamSize,
                                      @Query("P1") String P1,
                                      @Query("P2") String P2,
                                      @Query("P3") String P3
    );

    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> api_get_P6_SQL_value(@Query("Service") String Service,
                                            @Query("Provider") String Provider,
                                            @Query("ParamSize") String ParamSize,
                                            @Query("P1") String P1,
                                            @Query("P2") String P2,
                                            @Query("P3") String P3,
                                            @Query("P4") String P4,
                                            @Query("P5") String P5,
                                            @Query("P6") String P6
    );

    @GET("services/SqlServices/value?response=application/json&")
    Call<ResponseBody> api_log_info(@Query("Service") String Service,
                                    @Query("Provider") String Provider,
                                    @Query("ParamSize") String ParamSize,
                                    @Query("P1") String P1,
                                    @Query("P2") String P2,
                                    @Query("P3") String P3,
                                    @Query("P4") String P4,
                                    @Query("P5") String P5,
                                    @Query("P6") String P6,
                                    @Query("P7") String P7,
                                    @Query("P8") String P8
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
