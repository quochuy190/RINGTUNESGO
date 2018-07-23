package com.neo.media.Fragment.CaNhan.Groups;

import android.util.Log;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.Listener.CallbackData;

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
        Log.i("group", "Get all Group");
        String Service = "GET_GROUPS";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;
        apiService.getAllGroup(new CallbackData<GROUPS>() {
            @Override
            public void onGetDataSuccess(ArrayList<GROUPS> arrayList) {
                Log.i("group", "Lỗi");
            }

            @Override
            public void onGetDataFault(Exception e) {
                Log.i("group", "Lỗi onGetDataFault");
               // fragmentGroups.hideDialogLoading();
                fragmentGroups.showMessage("Lỗi kết nối");
           //     fragmentGroups.showGroups(null);
            }

            @Override
            public void onGetObjectDataSuccess(GROUPS Object) {
                if (Object!=null){
                    Log.i("group", Object.getTotal());
                    fragmentGroups.showGroups(Object.getGroup());
                }else {
                    Log.i("group", "Không có data");
                }


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
