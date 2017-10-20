package com.neo.media.ApiService;

import com.neo.media.Config.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by QQ on 9/29/2017.
 */

public interface ApiServicePostImp {

    @POST("/m/getnumber.jsp")
    @FormUrlEncoded
    Call<ResponseBody> api_get_3g(@Field("userid") String userid );

    Retrofit retrofitpost = new Retrofit.Builder()
            .baseUrl(Constant.BASE_URL_POST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
