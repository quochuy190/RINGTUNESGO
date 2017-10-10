package com.neo.ringtunesgo.Fragment.Stop_Pause;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 7/27/2017.
 */

public class Presenter_StopPause implements Stop_Pause_Interface.Presenter {
    ApiService apiService;
    FragmentStopPause fragmentStopPause;

    public Presenter_StopPause(FragmentStopPause fragmentStopPause) {
        this.fragmentStopPause = fragmentStopPause;
        apiService = new ApiService();
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
                    fragmentStopPause.showInfo_User(Object);

            }
        }, Service, Provider, ParamSize, P1, P2);
    }

    @Override
    public void stop_Service(String sessionID, String msisdn) {
        String Service = "DELETE_SUBSCRIBER";
        String Provider = "default";
        String ParamSize = "2";

        apiService.api_delete_subcriber(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0){
                    fragmentStopPause.show_stop_service(arrayList);
                }

            }

            @Override
            public void onGetDataFault(Exception e) {
                //fragmentStopPause.showMessage("Huỷ dịch vụ thất bại");
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sessionID, msisdn);

    }

    @Override
    public void pause_resume_subscriber(String sessionID, String msisdn,String status) {
        String Service = "UPDATE_SUBSCRIBER";
        String Provider = "default";
        String ParamSize = "3";
        apiService.api_update_subcriber(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0){
                    fragmentStopPause.show_resue_service(arrayList);
                }

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sessionID, msisdn,status);


    }

    @Override
    public void change_stype_phone(String input) {

    }

    @Override
    public void add_subcriber(String sessionID, String msisdn, String PKG_CODE) {
        String Service = "ADD_SUBSCRIBER";
        String Provider = "default";
        String ParamSize = "3";
        apiService.api_update_subcriber(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0){
                    fragmentStopPause.show_add_service(arrayList);
                }
            }
            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sessionID, msisdn,PKG_CODE);
    }
}
