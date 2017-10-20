package com.neo.media.Fragment.InfoUser;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.Info_User;
import com.neo.media.Fragment.FragmentInfo;
import com.neo.media.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 10/5/2017.
 */

public class PresenterInfoUser implements InfoUserInterface.Presenter {
    ApiService apiService;
    FragmentInfo fragmentInfo;

    public PresenterInfoUser(FragmentInfo fragmentInfo) {
        this.apiService = new ApiService();
        this.fragmentInfo = fragmentInfo;
    }

    @Override
    public void getInfoUser(String msisdn, String userid) {

        String Service = "get_profile";
        String Provider = "default";
        String ParamSize = "2";

        apiService.api_get_infouser(new CallbackData<Info_User>() {
            @Override
            public void onGetDataSuccess(ArrayList<Info_User> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Info_User Object) {
                fragmentInfo.showGetInfoUser(Object);
            }
        },Service, Provider, ParamSize, msisdn, userid);


    }

    @Override
    public void update_profile(String name, String msisdn, String datereg, String sex, String brithday, String userid) {
        fragmentInfo.showDialogLoading();
        String Service = "update_profile";
        String Provider = "default";
        String ParamSize = "6";

        apiService.api_update_infouser(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragmentInfo.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentInfo.hideDialogLoading();
                fragmentInfo.showUpdateInfo(new String());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragmentInfo.hideDialogLoading();
                fragmentInfo.showUpdateInfo(Object);
            }
        },Service, Provider, ParamSize, name, msisdn, datereg, sex, brithday, userid);
    }
}
