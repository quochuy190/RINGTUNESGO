package com.neo.media.Fragment.CaNhan.Groups.AddGroup;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;
import com.neo.media.untils.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by QQ on 9/26/2017.
 */

public class PresenterAddGroup implements AddGroupInterface.Presenter {
    ApiService apiService;
    FragmentAddGroup fragmentAddGroup;

    public PresenterAddGroup(FragmentAddGroup fragmentAddGroup) {
        this.fragmentAddGroup = fragmentAddGroup;
        apiService = new ApiService();
    }

    @Override
    public void addGroup(String sesionID, String msisdn, String group_name, String all_phone) {
        fragmentAddGroup.showDialogLoading();
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
                if (arrayList.size() > 0)
                    fragmentAddGroup.showaddGroups(arrayList);
                else {
                    fragmentAddGroup.hideDialogLoading();
                    fragmentAddGroup.showaddGroups(new ArrayList<String>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentAddGroup.hideDialogLoading();
                fragmentAddGroup.showaddGroups(new ArrayList<String>());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragmentAddGroup.hideDialogLoading();
                fragmentAddGroup.showaddGroups(new ArrayList<String>());

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void getConllection(String sesionID, String msisdn) {
        fragmentAddGroup.showDialogLoading();
        String Service = "GET_MYLIST";
        String Provider = "default";
        String ParamSize = "2";

        apiService.getAllConllection(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {
                if (arrayList.size() > 0)
                    fragmentAddGroup.showConllection(arrayList);
                else {
                    fragmentAddGroup.hideDialogLoading();
                    fragmentAddGroup.showConllection(new ArrayList<Item>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentAddGroup.hideDialogLoading();
                fragmentAddGroup.showConllection(new ArrayList<Item>());
            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {
                fragmentAddGroup.hideDialogLoading();
                fragmentAddGroup.showConllection(new ArrayList<Item>());
            }
        }, Service, Provider, ParamSize, sesionID, msisdn);
    }

    @Override
    public void add_profile(String sesionID, String msisdn, String content_id, String caller_type
            , String caller_id, String from_time, String to_time) {
        String Service = "ADD_PROFILE_WITH_TIMER";
        String Provider = "default";
        String ParamSize = "7";
        String call_id = PhoneNumber.convertTo84PhoneNunber(caller_id);
        apiService.add_Profiles(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                    fragmentAddGroup.show_add_profile_buygroup(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentAddGroup.show_add_profile_buygroup(new ArrayList<String>());
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragmentAddGroup.show_add_profile_buygroup(new ArrayList<String>());
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, content_id, caller_type, call_id, from_time, to_time);
    }

    public void get_info_songs_collection(String id, String userID) {
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";

        apiService.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {

                if (fragmentAddGroup != null)
                    fragmentAddGroup.show_list_songs_collection(arrayList);
                // fragmentConllection.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, id, userID);
    }
}
