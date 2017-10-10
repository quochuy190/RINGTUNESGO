package com.neo.ringtunesgo.Fragment.Groups;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.CRBTModel.GROUPS;
import com.neo.ringtunesgo.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 7/24/2017.
 */

public class PresenterGroups implements GroupInterface.Presenter {
    ApiService apiService;
    FragmentGroups fragmentGroups;

    public PresenterGroups(FragmentGroups fragmentGroups) {
        this.fragmentGroups = fragmentGroups;
        apiService = new ApiService();
    }

    @Override
    public void getAllGroups(String sesionID, String msisdn, String group_id) {
        String Service = "GET_GROUPS";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;


        apiService.getAllGroup(new CallbackData<GROUPS>() {
            @Override
            public void onGetDataSuccess(ArrayList<GROUPS> arrayList) {

                //  if ()
                //fragmentGroups.showGroups(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroups.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(GROUPS Object) {

                    fragmentGroups.showGroups(Object.getGroup());


            }
        }, Service, Provider, ParamSize, P1, P2, P3);
    }

    @Override
    public void addGroup(String sesionID, String msisdn, String group_name, String all_phone) {
        String Service = "ADD_GROUP";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_name;
        String P4 = all_phone;

        apiService.add_Group(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {

                fragmentGroups.showaddGroups(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                if (Object != null) {

                }

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void deleteGroup(String sesionID, String msisdn, final String group_id) {
        String Service = "DELETE_GROUP";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;
        apiService.deleteGroup(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                arrayList.add(group_id);
                fragmentGroups.showdeleteGroups(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3);

    }


}
