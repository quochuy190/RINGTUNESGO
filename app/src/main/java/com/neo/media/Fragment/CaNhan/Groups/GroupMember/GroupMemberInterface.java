package com.neo.media.Fragment.CaNhan.Groups.GroupMember;

import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Model.Ringtunes;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by QQ on 7/24/2017.
 */

public interface GroupMemberInterface {
    interface Presenter{
        void getSongsSame(String idSinger, String page, String index);
        void getConllection(String sesionID,String msisdn, String content_id);
        void getAllMember(String sesionID, String msisdn,String group_id);
        void add_cli_to_group(String sesionID, String msisdn,String groupID, String caller_msisdn);
        void delete_cli_to_group(String sesionID, String msisdn,String groupID, String caller_msisdn);
        void getProfiles_By_GroupId(String sesionID, String msisdn,String caller_id, String caller_type);
        void deleteProfile(String sesionID, String msisdn,String profile_id);
        void deleteGroup(String sesionID, String msisdn,String group_id );
        void updateProfile(String sesionID, String msisdn,String profile_id, String content_id, String caller_type
                    , String caller_id, String from_time, String to_time);
        void add_profile(String sesionID, String msisdn, String content_id, String caller_type
                , String caller_id, String from_time, String to_time);
        void get_info_songs_collection(String id, String userID);
    }
    interface View {
        void showConllection(List<Item> lisItem, String content_id);
        void showGroupMember(GROUPS groups);
        void show_add_cli_group(JSONObject mJsonObject);
        void showMessage(String message);
        void showDelete(CLI clis);
        void showAllProfiles(PROFILE listProfiles);
        void showErrorDeleteProfile(List<String> list);
        void showdeleteGroups(List<String> list);
        void show_update_profile(List<String> string);
        void show_list_songs_collection(List<Ringtunes> listSongs);
    }
}
