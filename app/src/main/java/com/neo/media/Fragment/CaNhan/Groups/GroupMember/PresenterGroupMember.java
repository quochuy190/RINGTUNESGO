package com.neo.media.Fragment.CaNhan.Groups.GroupMember;

import android.content.SharedPreferences;
import android.util.Log;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUPS;
import com.neo.media.CRBTModel.Item;
import com.neo.media.CRBTModel.PROFILE;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;
import com.neo.media.untils.PhoneNumber;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by QQ on 7/24/2017.
 */

public class PresenterGroupMember implements GroupMemberInterface.Presenter {
    FragmentGroupMember fragmentGroupMember;

    ApiService apiGetGroups;
    SharedPreferences pre;

    public PresenterGroupMember(FragmentGroupMember fragmentGroupMember) {
        this.fragmentGroupMember = fragmentGroupMember;
        apiGetGroups = new ApiService();

    }

    @Override
    public void getAllMember(String sesionID, String msisdn, String group_id) {
       // fragmentGroupMember.showDialogLoading();
        String Service = "GET_GROUP_BY_ID";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;
        apiGetGroups.getAllGroup(new CallbackData<GROUPS>() {
            @Override
            public void onGetDataSuccess(ArrayList<GROUPS> arrayList) {
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.showGroupMember(new GROUPS());
            }

            @Override
            public void onGetObjectDataSuccess(GROUPS Object) {
                fragmentGroupMember.showGroupMember(Object);
            }
        }, Service, Provider, ParamSize, P1, P2, P3);

    }

    @Override
    public void add_cli_to_group(String sesionID, String msisdn, String groupID, String caller_msisdn) {
        fragmentGroupMember.showDialogLoading();
        String Service = "ADD_CLI_TO_GROUP";
        String Provider = "default";
        String ParamSize = "4";
        apiGetGroups.add_cli_to_group(new CallbackData<JSONObject>() {
            @Override
            public void onGetDataSuccess(ArrayList<JSONObject> arrayList) {
               /* fragmentGroupMember.hideDialogLoading();
                if (arrayList.size() > 0) {
                    fragmentGroupMember.show_add_cli_group(arrayList);
                } else fragmentGroupMember.show_add_cli_group(new ArrayList<CLI>());*/

            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
                fragmentGroupMember.show_add_cli_group(null);
            }

            @Override
            public void onGetObjectDataSuccess(JSONObject Object) {
                fragmentGroupMember.show_add_cli_group(Object);

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, groupID, caller_msisdn);
    }

    @Override
    public void delete_cli_to_group(String sesionID, String msisdn, String groupID, String caller_msisdn) {
        fragmentGroupMember.showDialogLoading();
        String Service = "DELETE_CLI_FROM_GROUP";
        String Provider = "default";
        String ParamSize = "4";
        apiGetGroups.delete_cli_to_group(new CallbackData<CLI>() {
            @Override
            public void onGetDataSuccess(ArrayList<CLI> arrayList) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(CLI Object) {
                fragmentGroupMember.hideDialogLoading();
                fragmentGroupMember.showDelete(Object);

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, groupID, caller_msisdn);
    }

    @Override
    public void getProfiles_By_GroupId(String sesionID, String msisdn, String caller_id, String caller_type) {
        String Service = "GET_PROFILES";
        String Provider = "default";
        String ParamSize = "4";

        apiGetGroups.getAllProfile(new CallbackData<PROFILE>() {
            @Override
            public void onGetDataSuccess(ArrayList<PROFILE> arrayList) {
                if (arrayList.size() > 0)
                    fragmentGroupMember.showAllProfiles(arrayList.get(0));
                else {

                    fragmentGroupMember.showAllProfiles(new PROFILE());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.showAllProfiles(new PROFILE());
            }

            @Override
            public void onGetObjectDataSuccess(PROFILE Object) {
                if (Object != null)
                    fragmentGroupMember.showAllProfiles(Object);
                else {
                    fragmentGroupMember.showAllProfiles(new PROFILE());
                }
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, caller_id, caller_type);

    }

    @Override
    public void deleteProfile(String sesionID, String msisdn, String profile_id) {
        fragmentGroupMember.showDialogLoading();
        String Service = "DELETE_PROFILE";
        String Provider = "default";
        String ParamSize = "3";
        apiGetGroups.api_delete_profile(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragmentGroupMember.hideDialogLoading();
                if (arrayList.size() > 0)
                    fragmentGroupMember.showErrorDeleteProfile(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragmentGroupMember.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, profile_id);
    }

    @Override
    public void deleteGroup(String sesionID, String msisdn, String group_id) {
        String Service = "DELETE_GROUP";
        String Provider = "default";
        String ParamSize = "3";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = group_id;
        apiGetGroups.deleteGroup(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                // arrayList.add(group_id);
                fragmentGroupMember.showdeleteGroups(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3);

    }

    @Override
    public void updateProfile(String sesionID, String msisdn, String profile_id, String content_id, String caller_type,
                              String caller_id, String from_time, String to_time) {
        fragmentGroupMember.showDialogLoading();
        String Service = "UPDATE_PROFILE_WITH_TIMER";
        String Provider = "default";
        String ParamSize = "8";
        String call_id = PhoneNumber.convertTo84PhoneNunber(caller_id);
        apiGetGroups.update_Profiles(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size() > 0) {
                    fragmentGroupMember.show_update_profile(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, profile_id, content_id, caller_type, call_id, from_time, to_time);
    }

    @Override
    public void getConllection(String sesionID, String msisdn, final String content_id) {
        String Service = "GET_MYLIST";
        String Provider = "default";
        String ParamSize = "2";

        apiGetGroups.getAllConllection(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {
                fragmentGroupMember.showConllection(arrayList, content_id);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {
                fragmentGroupMember.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn);

    }


    @Override
    public void getSongsSame(String idSinger, String page, String index) {
        String Service = "singer_detail_main_service";
        String Provider = "default";
        String ParamSize = "5";
        String P1 = idSinger;
        String P2 = "1";
        String P3 = page;
        String P4 = index;

        apiGetGroups.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentGroupMember.hideDialogLoading();
                //viewRingtunes.showSongsBySingerId(arrayList);
                if (arrayList.size() > 0) {
                    Log.i("abc", arrayList.size() + "");
                    fragmentGroupMember.showLisSongsSame(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentGroupMember.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void add_profile(String sesionID, String msisdn, String content_id, String caller_type
            , String caller_id, String from_time, String to_time) {
        fragmentGroupMember.showDialogLoading();
        String Service = "ADD_PROFILE_WITH_TIMER";
        String Provider = "default";
        String ParamSize = "7";
        String call_id = PhoneNumber.convertTo84PhoneNunber(caller_id);
        apiGetGroups.add_Profiles(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragmentGroupMember.show_update_profile(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, content_id, caller_type, call_id, from_time, to_time);
    }

    @Override
    public void get_info_songs_collection(String id, String userID) {
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";

        apiGetGroups.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentGroupMember.show_list_songs_collection(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.show_list_songs_collection(new ArrayList<Ringtunes>());
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, id, userID);

    }

    public void api_suggestion_play(String Singer_id, String song_id, String UserID) {
        String Service = "suggestion_groups";
        String Provider = "default";
        String ParamSize = "3";


        apiGetGroups.api_suggestion_play(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentGroupMember.showLisSongsSame(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentGroupMember.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentGroupMember.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, Singer_id, song_id, UserID);
    }
}
