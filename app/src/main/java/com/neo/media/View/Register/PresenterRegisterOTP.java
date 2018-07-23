package com.neo.media.View.Register;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.subscriber;
import com.neo.media.Listener.CallbackData;
import com.neo.media.View.Login.InterfaceLogin;

import java.util.ArrayList;

/**
 * Created by QQ on 9/5/2017.
 */

public class PresenterRegisterOTP implements InterfaceRegisterOTP.Presenter, InterfaceLogin.Presenter {
    ApiService apiService;
    ActivityRegisterOTP activityRegisterOTP;


    public PresenterRegisterOTP(ActivityRegisterOTP activityRegisterOTP) {
        this.activityRegisterOTP = activityRegisterOTP;
        apiService = new ApiService();
    }

    @Override
    public void getOTPcode(String msisdn, String sUserID) {
        String Service = "OTP";
        String Provider = "default";
        String ParamSize = "2";
        apiService.api_get_code_otp(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size() > 0) {
                    activityRegisterOTP.showGetOTP(arrayList);
                } else {
                    activityRegisterOTP.showGetOTP(new ArrayList<String>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                activityRegisterOTP.showGetOTP(new ArrayList<String>());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                activityRegisterOTP.showGetOTP(new ArrayList<String>());

            }
        }, Service, Provider, ParamSize, msisdn, sUserID);
    }

    @Override
    public void xacthucOTP(String msisdn, String sUserID, String maOTP) {
       // activityRegisterOTP.showDialogLoading();
        String Service = "CF_OTP";
        String Provider = "default";
        String ParamSize = "3";
        apiService.api_confirm_otp(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size() > 0) {
                    activityRegisterOTP.showConfirmOTP(arrayList);
                } else {
                    activityRegisterOTP.showConfirmOTP(new ArrayList<String>());
                }


            }

            @Override
            public void onGetDataFault(Exception e) {
                activityRegisterOTP.showConfirmOTP(new ArrayList<String>());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                activityRegisterOTP.showConfirmOTP(new ArrayList<String>());
            }
        }, Service, Provider, ParamSize, msisdn, maOTP, sUserID);
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
                    activityRegisterOTP.showDataLogin(arrayList);
                } else {
                    activityRegisterOTP.showDataLogin(new ArrayList<String>());
                }

            }

            @Override
            public void onGetDataFault(Exception e) {
                activityRegisterOTP.showDataLogin(new ArrayList<String>());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                activityRegisterOTP.showDataLogin(new ArrayList<String>());

            }
        }, Service, Provider, ParamSize, pass, username);
    }

    @Override
    public void LoginVinaphonePortal(String username, String password, String userId) {

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
                activityRegisterOTP.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {
                activityRegisterOTP.hideDialogLoading();
                activityRegisterOTP.showInfo_User(new subscriber());
            }

            @Override
            public void onGetObjectDataSuccess(subscriber Object) {
                activityRegisterOTP.hideDialogLoading();
                if (Object != null && Object.getSUBID().length() > 0)
                    activityRegisterOTP.showInfo_User(Object);
                else activityRegisterOTP.showInfo_User(new subscriber());
            }
        }, Service, Provider, ParamSize, P1, P2);
    }
}
