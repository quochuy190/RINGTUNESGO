package com.neo.media.Fragment.Home.Topic.Presenter;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Fragment.Home.Topic.View.FragmentTocpic;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Topic;

import java.util.ArrayList;

/**
 * Created by QQ on 7/8/2017.
 */

public class PresenterTopic implements PresenterTopicImpl {
    ApiService apiService;
    FragmentTocpic fragmentAlbum;

    public PresenterTopic(FragmentTocpic fragmentAlbum) {
        this.fragmentAlbum = fragmentAlbum;
        apiService = new ApiService();
    }

    @Override
    public void getallElement(String page, String index) {
        String Service = "hot_package_hot_service";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = page;
        String P2 = index;
        apiService.getAllTopic(new CallbackData<Topic>() {
            @Override
            public void onGetDataSuccess(ArrayList<Topic> arrayList) {
               fragmentAlbum.showAllTopic(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Topic Object) {

            }
        }, Service, Provider, ParamSize, P1, P2);

    }
}
