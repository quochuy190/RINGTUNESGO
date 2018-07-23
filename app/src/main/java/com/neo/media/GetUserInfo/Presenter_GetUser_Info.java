package com.neo.media.GetUserInfo;

import android.util.Log;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 4/4/2018.
 */

public class Presenter_GetUser_Info implements IpmGetUserInfo.Presenter {
    ApiService apiService;
    IpmGetUserInfo.View view;

    public Presenter_GetUser_Info(IpmGetUserInfo.View view) {
        apiService = new ApiService();
        this.view = view;
    }

    @Override
    public void api_get_detail_subsriber(String sesionID, String msisdn) {
        String Service = "GET_SUBSCRIBER_DETAILS";
        String Provider = "default";
        String ParamSize = "2";
        String P1 = sesionID;
        String P2 = msisdn;

        apiService.get_SUBSCRIBER_DETAILS(new CallbackData<subscriber>() {
            @Override
            public void onGetDataSuccess(ArrayList<subscriber> arrayList) {
                //view.hideDialogLoading();

            }

            @Override
            public void onGetDataFault(Exception e) {
              //  fragmentCanhan.hideDialogLoading();
                view.show_error_api();
                Log.i("get_sub", "dataFault");
            }

            @Override
            public void onGetObjectDataSuccess(subscriber Object) {
               // fragmentCanhan.hideDialogLoading();
                Log.i("get_sub", "success");
               // view.show_detail_subsriber(Object);
                if (Object != null ){
                    view.show_detail_subsriber(Object);
                }else {
                    view.show_detail_subsriber(new subscriber());
                }


            }
        }, Service, Provider, ParamSize, P1, P2);

    }

    @Override
    public void api_subsriber_sms(String msisdn, String m_click) {
        String Service = "DKSMS";
        String Provider = "default";
        String ParamSize = "2";
        String P1 = msisdn;

        apiService.api_subscriber_sms(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                //view.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {
                //  fragmentCanhan.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String obj) {
                // fragmentCanhan.hideDialogLoading();



            }
        }, Service, Provider, ParamSize, P1, m_click);
    }

    @Override
    public void login_3g(String userid) {
        apiService.login_3g(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                view.show_login_3g(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                view.show_error_api();
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
                if (arrayList.size() > 0) {
                    //Constant.sMSISDN = Prefs.with(this).getString("msisdn", "");
                    view.showDataLogin(arrayList);

                } else {
                    view.showDataLogin(new ArrayList<String>());
                }

            }

            @Override
            public void onGetDataFault(Exception e) {
                view.show_error_api();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                //viewXacthuc.showDataLogin(new ArrayList<String>());
            }
        }, Service, Provider, ParamSize, pass, username);
    }
}
