package com.neo.ringtunesgo.Fragment.Profiles;

import com.neo.ringtunesgo.CRBTModel.PROFILE;

import java.util.List;

/**
 * Created by QQ on 7/25/2017.
 */

public interface ProfilesInterface {
    interface Presenter{
        void getAllProfiles(String sesionID, String msisdn,String caller_id, String caller_type);
        void deleteProfile(String sesionID, String msisdn,String profile_id);
    }
    interface View{
        void showAllProfiles(List<PROFILE> listProfiles);
        void showErrorDeleteProfile(List<String> list);
    }
}
