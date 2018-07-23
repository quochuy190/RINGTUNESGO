package com.neo.media.Fragment.DetailSongs.Presenter;


import com.neo.media.ApiService.ApiService;
import com.neo.media.Config.Constant;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;

import java.util.ArrayList;

/**
 * Created by QQ on 7/9/2017.
 */

public class Presenter_Detail_Ringtunes implements Presenter_Detail_RingtunesImpl {
    private ApiService apiService;
    private FragmentSongs viewRingtunes;


    public Presenter_Detail_Ringtunes(FragmentSongs viewRingtunes) {
        apiService = new ApiService();
        this.viewRingtunes = viewRingtunes;
    }

    @Override
    public void getByTopic(String id, String page, String number) {
      // viewRingtunes.showDialogLoading();
        String Service = "hot_detail_detail_service";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = id;
        apiService.getRingtunes_ByTocpic(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                viewRingtunes.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                viewRingtunes.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, P1, page, number, Constant.USER_ID);
    }
    public void getByEvent(String Service,String id, String page, String number, String userID) {
       // viewRingtunes.showDialogLoading();
        String Provider = "default";
        String ParamSize = "4";
        apiService.getRingtunes_ByTocpic(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, id, page, number, userID);
    }
    @Override
    public void getByAlbum(String id, String type, String page, String number) {
      //  viewRingtunes.hideDialogLoading();
      //  viewRingtunes.showDialogLoading();
        String Service = "album_detail_detail_service";
        String Provider = "default";
        String ParamSize = "5";
        String P1 = id;
        String P2 = "300";
        String P3 = page;
        String P4 = number;

        apiService.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void getByType(String id, String type, String page, String nuber) {
     //   viewRingtunes.hideDialogLoading();
       // viewRingtunes.showDialogLoading();
        String Service = "category_detail_detail_service";
        String Provider = "default";
        String ParamSize = "5";
        String P1 = id;
        String P2 = type;
        String P3 = page;
        String P4 = nuber;

        apiService.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void getBySinger(String id, String type, String page, String nuber) {
     //   viewRingtunes.hideDialogLoading();
      //  viewRingtunes.showDialogLoading();
        String Service = "singer_detail_main_service";
        String Provider = "default";
        String ParamSize = "5";
        String P1 = id;
        String P2 = type;
        String P3 = page;
        String P4 = nuber;

        apiService.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void log_Info_Charge_Server(String P1, String P2, String P3, String P4, String P5) {
     //   viewRingtunes.hideDialogLoading();
      //  viewRingtunes.showDialogLoading();
        String Service = "log_info_charge_service";
        String Provider = "default";
        String ParamSize = "5";
        apiService.logInfo_ActionUser(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                viewRingtunes.hideDialogLoading();
                if (arrayList.get(0).equals("1"))
                    viewRingtunes.showMessage("Log thông tin Server thành công");
                else viewRingtunes.showMessage("Log thông tin Server không thành công");
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4, P5);
    }

    @Override
    public void getByRingtunesHot(String top_type, String page, String index) {
       // viewRingtunes.hideDialogLoading();
        //viewRingtunes.showDialogLoading();
        String Service = "ringtunes_hot_detail_service";
        String Provider = "default";
        String ParamSize = "4";

        apiService.getRingtunesHot(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, top_type, page, index);
    }

    @Override
    public void getByRingtunesNew(String top_type, String page, String index) {
      //  viewRingtunes.hideDialogLoading();
     //   viewRingtunes.showDialogLoading();
        String Service = "index_ringtune_new_service";
        String Provider = "default";
        String ParamSize = "4";

        apiService.getRingtunesHot(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewRingtunes.hideDialogLoading();
                viewRingtunes.showListRingtunes(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, top_type, page, index);
    }


}
