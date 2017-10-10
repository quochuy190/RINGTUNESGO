package com.neo.ringtunesgo.Fragment.Groups.AddGroup;

import com.neo.ringtunesgo.CRBTModel.Item;

import java.util.List;

/**
 * Created by QQ on 9/26/2017.
 */

public interface AddGroupInterface {
    interface Presenter{
        void addGroup(String sesionID, String msisdn,String group_name, String all_phone);
        void getConllection(String sesionID,String msisdn);
        void add_profile(String sesionID, String msisdn, String content_id, String caller_type
                , String caller_id, String from_time, String to_time);
    }
    interface View{
        void show_add_profile_buygroup(List<String> list);
        void showaddGroups(List<String> list);
        void showConllection(List<Item> listItems);
    }
}
