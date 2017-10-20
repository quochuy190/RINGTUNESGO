package com.neo.media.Fragment.Profiles;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Listener.CallbackData;

import java.util.ArrayList;

/**
 * Created by QQ on 7/25/2017.
 */

public class PresenterProfiles implements ProfilesInterface.Presenter {

    ApiService apiService;
    FragmentProfiles fragmentProfiles;

    public PresenterProfiles(FragmentProfiles fragmentProfiles) {
        this.fragmentProfiles = fragmentProfiles;
        apiService = new ApiService();
    }

    @Override
    public void getAllProfiles(String sesionID, String msisdn,String caller_id, String caller_type) {
        fragmentProfiles.showDialogLoading();
        String Service = "GET_PROFILES";
        String Provider = "default";
        String ParamSize = "4";


        apiService.getAllProfile(new CallbackData<PROFILE>() {
            @Override
            public void onGetDataSuccess(ArrayList<PROFILE> arrayList) {
                fragmentProfiles.hideDialogLoading();
                fragmentProfiles.showAllProfiles(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentProfiles.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(PROFILE Object) {
                fragmentProfiles.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, caller_id, caller_type);
    }

    @Override
    public void deleteProfile(String sesionID, String msisdn, String profile_id) {
        String Service = "DELETE_PROFILE";
        String Provider = "default";
        String ParamSize = "3";
        apiService.api_delete_profile(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size()>0)
                    fragmentProfiles.showErrorDeleteProfile(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, profile_id);
    }

}
