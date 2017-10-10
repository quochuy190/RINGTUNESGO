package com.neo.ringtunesgo.Fragment.Groups.GroupMember;

import com.neo.ringtunesgo.CRBTModel.CLI;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.Model.PhoneContactModel;
import com.neo.ringtunesgo.Model.Ringtunes;

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
        void showGroupMember(List<PhoneContactModel> phoneContactModels);
        void show_add_cli_group(List<String> list);
        void showMessage(String message);
        void showDelete(CLI clis);
        void showAllProfiles(PROFILE listProfiles);
        void showErrorDeleteProfile(List<String> list);
        void showdeleteGroups(List<String> list);
        void show_update_profile(List<String> string);
        void show_list_songs_collection(List<Ringtunes> listSongs);
    }
}
