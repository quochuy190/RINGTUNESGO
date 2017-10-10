package com.neo.ringtunesgo.Fragment.Profiles.Add_Profile;

import com.neo.ringtunesgo.CRBTModel.GROUPS;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Model.Ringtunes;

import java.util.List;

/**
 * Created by QQ on 7/26/2017.
 */

public interface Add_Profile_Inteface {
    interface Presenter{
        void getConllection(String page,String index);
        void getAllGroup(String sesionID, String msisdn,String group_id);
        void add_profile(String sesionID, String msisdn, String content_id, String caller_type
                , String caller_id, String from_time, String to_time);
        void updateProfile(String sesionID, String msisdn,String profile_id, String content_id, String caller_type
                , String caller_id, String from_time, String to_time);
        void get_info_songs_collection(String id, String userID);
    }
    interface View{
        void showaddProfile(List<String> list);
        void showConllection(List<Item> listItems);
        void showMessage(String message);
        void showGroups(GROUPS groups);
        void show_update_profile(List<String> string);
        void show_list_songs_collection(List<Ringtunes> listSongs);
    }
}
