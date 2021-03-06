package com.neo.media.Fragment.Home.Presenter;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Fragment.Home.View.FragmentHome;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Banner;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;
import com.neo.media.Model.Topic;
import com.neo.media.Model.Type;

import java.util.ArrayList;

/**
 * Created by QQ on 7/7/2017.
 */

public class PresenterHome implements PresenterHomeImpl {
    private ApiService apiService;
    private FragmentHome viewfragmentHome;

    public PresenterHome(FragmentHome viewfragmentHome) {
        this.viewfragmentHome = viewfragmentHome;
        apiService = new ApiService();

    }


    @Override
    public void getLisTopoc(String P1, String P2) {
        String Service = "index_package_hot_service";
        String Provider = "default";
        String ParamSize = "3";
        apiService.getAllTopic(new CallbackData<Topic>() {
            @Override
            public void onGetDataSuccess(ArrayList<Topic> arrayList) {
                viewfragmentHome.showListTopic(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Topic Object) {

            }
        }, Service, Provider, ParamSize, P1, P2);
    }

    @Override
    public void getRingtunesHot(String P1, String P2) {
        String Service = "index_ringtune_hot_service";
        String Provider = "default";
        String ParamSize = "4";
        String P1_value = "252";

        apiService.getRingtunesHot(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewfragmentHome.showListRingtunesHot(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1_value, P1, P2);
    }

    @Override
    public void getRingtunesNew(String P2, String P3) {
        String Service = "index_ringtune_new_service";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = "251";

        apiService.getRingtunesHot(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                viewfragmentHome.showListRingtunesNew(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3);
    }

    @Override
    public void getListSinger(String P1, String P2) {
        String Service = "index_singer_service";
        String Provider = "default";
        String ParamSize = "3";

        apiService.getAllSinger(new CallbackData<Singer>() {
            @Override
            public void onGetDataSuccess(ArrayList<Singer> arrayList) {
                viewfragmentHome.showListSinger(arrayList);
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
    public void getListType(String P1, String P2) {
        String Service = "index_category_service";
        String Provider = "default";
        String ParamSize = "3";

        apiService.getAllType(new CallbackData<Type>() {
            @Override
            public void onGetDataSuccess(ArrayList<Type> arrayList) {
                viewfragmentHome.showListType(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Type Object) {

            }
        }, Service, Provider, ParamSize, P1, P2);
    }

    @Override
    public void getbanner_header(String userid) {
        String Service = "header_slides_service2";
        String Provider = "default";
        String ParamSize = "1";

        apiService.api_header_slides_service(new CallbackData<Banner>() {
            @Override
            public void onGetDataSuccess(ArrayList<Banner> arrayList) {
                if (arrayList.size()>0){
                    viewfragmentHome.showlistBanner(arrayList);
                }else
                    viewfragmentHome.showlistBanner(new ArrayList<Banner>());


            }

            @Override
            public void onGetDataFault(Exception e) {
                viewfragmentHome.showlistBanner(new ArrayList<Banner>());
            }

            @Override
            public void onGetObjectDataSuccess(Banner Object) {

            }
        }, Service, Provider, ParamSize, userid);
    }

    public void promotion_idx(final String Service, String userid) {
        if (Service.equals("event_idx"))
            viewfragmentHome.showDialogLoading();
        String Provider = "default";
        String ParamSize = "1";
        apiService.api_promotion_idx(new CallbackData<Topic>() {
            @Override
            public void onGetDataSuccess(ArrayList<Topic> arrayList) {
                viewfragmentHome.hideDialogLoading();
                if (Service.equals("promotion_idx"))
                    viewfragmentHome.showPromotion(arrayList);
                if (Service.equals("event_idx"))
                    viewfragmentHome.showEvent(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                viewfragmentHome.hideDialogLoading();
                viewfragmentHome.showPromotion(new ArrayList<Topic>());
            }

            @Override
            public void onGetObjectDataSuccess(Topic Object) {

            }
        }, Service, Provider, ParamSize, userid);
    }

    public void getslogan_idx(final String Service, String userid) {
        String Provider = "default";
        String ParamSize = "1";

        apiService.api_promotion_idx(new CallbackData<Topic>() {
            @Override
            public void onGetDataSuccess(ArrayList<Topic> arrayList) {
                if (Service.equals("slogan_idx")) {
                    viewfragmentHome.showslogan_idx(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Topic Object) {

            }
        }, Service, Provider, ParamSize, userid);
    }

    @Override
    public void getrankRingtunes(String index, String number, String userID) {
        String Service = "ranking_detail_service";
        String Provider = "default";
        String ParamSize = "3";

        apiService.getrankRingtunes(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                if (arrayList.size() > 0) {
                    // viewfragmentHome.showListRingtunesNew(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, index, number, userID);
    }
}
