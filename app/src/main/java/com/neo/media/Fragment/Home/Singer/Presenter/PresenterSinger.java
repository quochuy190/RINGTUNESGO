package com.neo.media.Fragment.Home.Singer.Presenter;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Fragment.Home.Singer.View.FragmentSinger;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;

import java.util.ArrayList;

/**
 * Created by QQ on 7/8/2017.
 */

public class PresenterSinger implements PresenterSingerImpl.Presenter {
    ApiService apiService;
    FragmentSinger fragmentSinger;
    Fragment_SingerDetail fragment_singerDetail;

    public PresenterSinger(FragmentSinger fragmentSinger) {
        this.fragmentSinger = fragmentSinger;
        apiService = new ApiService();
    }

    public PresenterSinger(Fragment_SingerDetail fragment_singerDetail) {
        apiService = new ApiService();
        this.fragment_singerDetail = fragment_singerDetail;
    }

    @Override
    public void getAllSinger(String page, String index) {
        String Service = "singer_main_service";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = page;
        String P2 = index;

        apiService.getAllSinger(new CallbackData<Singer>() {
            @Override
            public void onGetDataSuccess(ArrayList<Singer> arrayList) {
                fragmentSinger.showAllTopic(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
            }

            @Override
            public void onGetObjectDataSuccess(Singer Object) {

            }
        }, Service, Provider, ParamSize, P1, P2);
    }

    @Override
    public void getDetailSinger(String singer_id) {
        String Service = "singer_detail_summary_service";
        String Provider = "default";
        String ParamSize = "2";

        apiService.getDetail_Singer(new CallbackData<Singer>() {
            @Override
            public void onGetDataSuccess(ArrayList<Singer> arrayList) {
                fragment_singerDetail.showSingerDetail(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragment_singerDetail.showSingerDetail(new ArrayList<Singer>());
            }

            @Override
            public void onGetObjectDataSuccess(Singer Object) {

            }
        }, Service, Provider, ParamSize, singer_id);
    }

    @Override
    public void getSongsBySinger(String singer_id, String page, String index) {
        String Service = "singer_detail_main_service";
        String Provider = "default";
        String ParamSize = "5";


        apiService.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragment_singerDetail.showSongbySinger(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, singer_id, "0", page, index);
    }
}
