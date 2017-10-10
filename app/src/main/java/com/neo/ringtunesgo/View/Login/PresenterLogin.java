package com.neo.ringtunesgo.View.Login;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 9/5/2017.
 */

public class PresenterLogin implements InterfaceLogin.Presenter {
    ApiService apiService;
    ActivityLogin viewLogin;
    FragmentDetailBuySongs fragmentDetailBuySongs;

    public PresenterLogin(ActivityLogin viewLogin) {
        this.viewLogin = viewLogin;
        apiService = new ApiService();
    }

    public PresenterLogin(FragmentDetailBuySongs fragmentDetailBuySongs) {
        this.fragmentDetailBuySongs = fragmentDetailBuySongs;
        apiService = new ApiService();
    }

    @Override
    public void Login(String username, String pass) {
        String Service = "login";
        String Provider = "default";
        String ParamSize = "2";

        apiService.api_login(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0){
                    viewLogin.showDataLogin(arrayList);
                }

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {


            }
        }, Service, Provider, ParamSize, pass, username);
    }

    @Override
    public void LoginVinaphonePortal(String username, String password, String userId) {
        String Service = "PORTAL";
        String Provider = "default";
        String ParamSize = "3";

        apiService.api_login_vinaportal(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0){
                    viewLogin.showDataLoginVinaphonePortal(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {


            }
        }, Service, Provider, ParamSize, username, password, userId);
    }

    @Override
    public void get_detail_subsriber(String sesionID, String msisdn) {
        String Service = "GET_SUBSCRIBER_DETAILS";
        String Provider = "default";
        String ParamSize = "2";
        String P1 = sesionID;
        String P2 = msisdn;

        apiService.get_SUBSCRIBER_DETAILS(new CallbackData<subscriber>() {
            @Override
            public void onGetDataSuccess(ArrayList<subscriber> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(subscriber Object) {
                if (Object != null && Object.getSUBID().length() > 0)
                    viewLogin.showInfo_User(Object);
            }
        }, Service, Provider, ParamSize, P1, P2);
    }
}
