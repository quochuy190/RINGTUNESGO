package com.neo.ringtunesgo.Fragment.Collection;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Fragment.Profiles.Add_Profile.Fragment_AddProfiles;
import com.neo.ringtunesgo.Listener.CallbackData;
import com.neo.ringtunesgo.Model.Ringtunes;

import java.util.ArrayList;

/**
 * Created by QQ on 7/24/2017.
 */

public class PresenterConllection implements ConllectionInteface.Presenter {
    //ApiGetMyList apiGetMyList;
    FragmentConllection fragmentConllection;
    Fragment_AddProfiles fragment_addProfiles;
    ApiService apiService;

    public PresenterConllection(FragmentConllection fragmentConllection) {
        this.fragmentConllection = fragmentConllection;
        //this.apiGetMyList = new ApiGetMyList();
        apiService = new ApiService();
    }

    public PresenterConllection(Fragment_AddProfiles fragment_addProfiles) {
        this.fragment_addProfiles = fragment_addProfiles;
        // this.apiGetMyList = new ApiGetMyList();
        apiService = new ApiService();
    }


    @Override
    public void getConllection(String sesionID, String msisdn) {
        fragmentConllection.showDialogLoading();
        String Service = "GET_MYLIST";
        String Provider = "default";
        String ParamSize = "2";

        apiService.getAllConllection(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {
                if (fragmentConllection != null) {
                    fragmentConllection.hideDialogLoading();
                    fragmentConllection.showConllection(arrayList);
                }
                if (fragment_addProfiles != null) {
                    fragment_addProfiles.hideDialogLoading();
                    fragment_addProfiles.showConllection(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                if (fragment_addProfiles != null) {
                    fragment_addProfiles.hideDialogLoading();
                    fragmentConllection.showConllection(new ArrayList<Item>());
                }

                if (fragmentConllection != null) {
                    fragmentConllection.hideDialogLoading();
                    fragmentConllection.showConllection(new ArrayList<Item>());
                }

            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {
                if (fragment_addProfiles != null)
                    fragment_addProfiles.hideDialogLoading();
                if (fragmentConllection != null)
                    fragmentConllection.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, sesionID, msisdn);

    }


    @Override
    public void deleteConllection(String sesionID, String msisdn, String item_id, String userId) {
        fragmentConllection.showDialogLoading();
        String Service = "DELETE_ITEM_FROM_MYLIST";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = sesionID;
        String P2 = msisdn;
        String P3 = item_id;
        String P4 = userId;

        apiService.delete_item_from_mylist(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                fragmentConllection.hideDialogLoading();
                //viewRingtunes.showSongsBySingerId(arrayList);
                if (arrayList.size() > 0) {
                    if (arrayList.get(0).equals("0")) {
                        fragmentConllection.showDeleteSuccessfull(arrayList);
                    }

                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentConllection.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                fragmentConllection.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
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

        apiService.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentConllection.hideDialogLoading();
                fragmentConllection.showLisSongsSame(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentConllection.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentConllection.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void get_info_songs_collection(String id, String userID) {
        fragmentConllection.showDialogLoading();
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";
        apiService.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentConllection.hideDialogLoading();
                fragmentConllection.show_list_songs_collection(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentConllection.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentConllection.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, id, userID);

    }

    public void api_suggestion_play(String Singer_id, String song_id, String UserID) {
        fragmentConllection.showDialogLoading();
        String Service = "suggestion_collection";
        String Provider = "default";
        String ParamSize = "3";


        apiService.api_suggestion_play(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentConllection.hideDialogLoading();
                fragmentConllection.showLisSongsSame(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentConllection.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentConllection.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, Singer_id, song_id, UserID);
    }
}
