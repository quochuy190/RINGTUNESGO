package com.neo.media.Fragment.Groups;

import com.neo.media.CRBTModel.GROUP;

import java.util.List;

/**
 * Created by QQ on 7/24/2017.
 */

public interface GroupInterface {
    interface Presenter{
        void getAllGroups(String sesionID, String msisdn,String group_id);
        void addGroup(String sesionID, String msisdn,String group_name, String all_phone);
        void deleteGroup(String sesionID, String msisdn,String group_id );


    }
    interface View{

        void showaddGroups(List<String> list);
        void showGroups(List<GROUP> groups);
        void showMessage(String message);
        void showdeleteGroups(List<String> list);
    }
}
