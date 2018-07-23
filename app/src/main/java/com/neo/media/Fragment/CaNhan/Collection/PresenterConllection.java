package com.neo.media.Fragment.CaNhan.Collection;

import com.neo.media.ApiService.ApiService;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;

import java.util.ArrayList;

/**
 * Created by QQ on 7/24/2017.
 */

public class PresenterConllection implements ConllectionInteface.Presenter {
    //ApiGetMyList apiGetMyList;
/*    FragmentConllection fragmentConllection;
    Fragment_AddProfiles fragment_addProfiles;*/
    ConllectionInteface.View view;
    ApiService apiService;

    public PresenterConllection(ConllectionInteface.View view) {
        apiService = new ApiService();
        this.view = view;
    }



    @Override
    public void getConllection(String sesionID, String msisdn) {
        //fragmentConllection.showDialogLoading();
        String Service = "GET_MYLIST";
        String Provider = "default";
        String ParamSize = "2";

        apiService.getAllConllection(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {
                    if (arrayList.size() > 0){
                        view.showConllection(arrayList);
                        MyApplication.lisItem.clear();
                        MyApplication.lisItem.addAll(arrayList);
                    }

                    else {
                        view.hideDialogLoading();
                        view.showConllection(new ArrayList<Item>());
                    }


            }

            @Override
            public void onGetDataFault(Exception e) {

                    view.showConllection(new ArrayList<Item>());

            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {


                    view.showConllection(new ArrayList<Item>());



            }
        }, Service, Provider, ParamSize, sesionID, msisdn);

    }


    @Override
    public void deleteConllection(String sesionID, String msisdn, String item_id, String userId) {
        view.showDialogLoading();
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
                view.hideDialogLoading();
                //viewRingtunes.showSongsBySingerId(arrayList);
                if (arrayList.size() > 0) {
                    view.showDeleteSuccessfull(arrayList);
                } else {
                    view.showDeleteSuccessfull(new ArrayList<String>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                view.showDeleteSuccessfull(new ArrayList<String>());
                view.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                view.showDeleteSuccessfull(new ArrayList<String>());
                view.hideDialogLoading();
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
                view.hideDialogLoading();
                if (arrayList.size() > 0) {
                    view.showLisSongsSame(arrayList);
                } else {
                    view.showLisSongsSame(new ArrayList<Ringtunes>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                view.hideDialogLoading();
                view.showLisSongsSame(new ArrayList<Ringtunes>());
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                view.hideDialogLoading();
                view.showLisSongsSame(new ArrayList<Ringtunes>());
            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    @Override
    public void get_info_songs_collection(String id, String userID) {
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";
        apiService.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                if (arrayList.size() > 0) {
                    view.show_list_songs_collection(arrayList);
                } else {
                    view.hideDialogLoading();
                    view.show_list_songs_collection(new ArrayList<Ringtunes>());
                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                view.show_list_songs_collection(new ArrayList<Ringtunes>());
                view.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                view.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, id, userID);

    }

    public void api_suggestion_play(String Singer_id, String song_id, String UserID) {

        String Service = "suggestion_collection";
        String Provider = "default";
        String ParamSize = "3";


        apiService.api_suggestion_play(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                //fragmentConllection.hideDialogLoading();
                view.showLisSongsSame(arrayList);

            }

            @Override
            public void onGetDataFault(Exception e) {
                view.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                view.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, Singer_id, song_id, UserID);
    }
}
