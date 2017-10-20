package com.neo.media.Fragment.Type.Presenter;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Fragment.Topic.Presenter.PresenterTopicImpl;
import com.neo.media.Fragment.Type.View.FragmentType;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Type;

import java.util.ArrayList;

/**
 * Created by QQ on 7/8/2017.
 */

public class PresenterType implements PresenterTopicImpl {
    ApiService apiService;
    FragmentType fragmentType;

    public PresenterType(FragmentType fragmentType) {
        this.fragmentType = fragmentType;
        apiService = new ApiService();
    }

  /*  "size=2&"
            + "P1=" + im.getPhoneNumber()
            + "&P2=" + im.getNumberFriendList()
            + "&src=default&sql="
            + "app.fan_get_friend");*/

    @Override
    public void getallElement(String page, String index) {
        String Service = "category_category_service";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = page;
        String P2 = index;

        apiService.getAllType(new CallbackData<Type>() {
            @Override
            public void onGetDataSuccess(ArrayList<Type> arrayList) {
                fragmentType.showAllTopic(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Type Object) {

            }
        }, Service, Provider, ParamSize, P1, P2);

    }
}
