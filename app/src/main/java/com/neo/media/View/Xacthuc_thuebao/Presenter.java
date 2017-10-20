package com.neo.media.View.Xacthuc_thuebao;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 8/31/2017.
 */

public class Presenter implements Xacthuc_Impl.Presenter{
    ApiService apiService;
    ActivityXacthuc viewXacthuc;

    public Presenter(ActivityXacthuc viewXacthuc) {
        this.viewXacthuc = viewXacthuc;
        apiService= new ApiService();
    }

    @Override
    public void login_3g(String userid) {
        apiService.login_3g(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                viewXacthuc.show_login_3g(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, userid);
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
                    viewXacthuc.showDataLogin(arrayList);
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
                    viewXacthuc.showInfo_User(Object);
            }
        }, Service, Provider, ParamSize, P1, P2);
    }
}
