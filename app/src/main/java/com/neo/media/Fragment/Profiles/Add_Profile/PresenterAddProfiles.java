package com.neo.media.Fragment.Profiles.Add_Profile;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;
import com.neo.media.untils.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by QQ on 7/26/2017.
 */

public class PresenterAddProfiles implements Add_Profile_Inteface.Presenter {
    ApiService apiService;
    Fragment_AddProfiles fragment_addProfiles;
    Fragment_EditProfile fragment_editProfile;

    public PresenterAddProfiles(Fragment_AddProfiles fragment_addProfiles) {
        this.fragment_addProfiles = fragment_addProfiles;
        apiService = new ApiService();
    }

    public PresenterAddProfiles(Fragment_EditProfile fragment_editProfiles) {
        this.fragment_editProfile = fragment_editProfiles;
        apiService = new ApiService();
    }

    @Override
    public void getConllection(String sesionID, String msisdn) {
        String Service = "GET_MYLIST";
        String Provider = "default";
        String ParamSize = "2";

        apiService.getAllConllection(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {
                if (fragment_addProfiles != null)
                    fragment_addProfiles.showConllection(arrayList);
                if (fragment_editProfile != null)
                    fragment_editProfile.showConllection(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {

            }
        }, Service, Provider, ParamSize, sesionID, msisdn);
    }

    @Override
    public void getAllGroup(String sesionID, String msisdn, String group_id) {
        String Service = "GET_GROUPS";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;


        apiService.getAllGroup(new CallbackData<GROUPS>() {
            @Override
            public void onGetDataSuccess(ArrayList<GROUPS> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(GROUPS Object) {
                if (Object != null) {
                    fragment_addProfiles.showGroups(Object);
                }

            }
        }, Service, Provider, ParamSize, P1, P2, P3);
    }

    @Override
    public void add_profile(String sesionID, String msisdn, String content_id, String caller_type
            , String caller_id, String from_time, String to_time) {
        fragment_addProfiles.showDialogLoading();
        String Service = "ADD_PROFILE_WITH_TIMER";
        String Provider = "default";
        String ParamSize = "7";

        apiService.add_Profiles(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragment_addProfiles.hideDialogLoading();
                if (arrayList.size() > 0) {
                    fragment_addProfiles.showaddProfile(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragment_addProfiles.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragment_addProfiles.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, content_id, caller_type, caller_id, from_time, to_time);
    }

    @Override
    public void updateProfile(String sesionID, String msisdn, String profile_id, String content_id, String caller_type,
                              String caller_id, String from_time, String to_time) {
        fragment_editProfile.showDialogLoading();
        String Service = "UPDATE_PROFILE_WITH_TIMER";
        String Provider = "default";
        String ParamSize = "8";
        String call_id = PhoneNumber.convertTo84PhoneNunber(caller_id);
        apiService.update_Profiles(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragment_editProfile.hideDialogLoading();
                if (arrayList.size() > 0) {
                    fragment_editProfile.show_update_profile(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragment_editProfile.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragment_editProfile.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn,profile_id, content_id, caller_type, call_id, from_time, to_time);
    }

    @Override
    public void get_info_songs_collection(String id, String userID) {
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";

        apiService.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                if (fragment_addProfiles != null)
                    fragment_addProfiles.show_list_songs_collection(arrayList);
                if (fragment_editProfile != null)
                    fragment_editProfile.show_list_songs_collection(arrayList);
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

