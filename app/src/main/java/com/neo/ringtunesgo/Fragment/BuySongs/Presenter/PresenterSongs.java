package com.neo.ringtunesgo.Fragment.BuySongs.Presenter;

import android.util.Log;

import com.neo.ringtunesgo.ApiService.ApiService;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.Listener.CallbackData;
import com.neo.ringtunesgo.Model.Comment;
import com.neo.ringtunesgo.Model.Ringtunes;

import java.util.ArrayList;

/**
 * Created by QQ on 7/19/2017.
 */

public class PresenterSongs implements PresenterSongsImpl.Presenter {
    //ApiContentItems apiContentItems;

    private ApiService apiSevice;
    FragmentDetailBuySongs fragmentDetailBuySongs;


    public PresenterSongs(FragmentDetailBuySongs fragmentDetailBuySongs) {
        apiSevice = new ApiService();
        this.fragmentDetailBuySongs = fragmentDetailBuySongs;
    }

    @Override
    public void login(String username, String pass) {

    }

    @Override
    public void log_Info_Charge_Server(String P1, String P2, String P3, String P4, String P5, String P6,String P7, String P8) {
        String Service = "log_info_charge_service";
        String Provider = "default";
        String ParamSize = "8";
        apiSevice.log_info_charge_service(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4, P5, P6,P7,P8);
    }

    @Override
    public void getSongsInformation(String sesionID, String msisdn, String content_id) {
        String Service = "GET_CONTENT_ITEMS_BY_CONTENT_ID";
        String Provider = "default";
        String ParamSize = "3";

        apiSevice.getContenItem(new CallbackData<Item>() {
            @Override
            public void onGetDataSuccess(ArrayList<Item> arrayList) {

            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Item Object) {
                fragmentDetailBuySongs.ShowItems(Object);
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, content_id);
    }

    @Override
    public void getComment_by_Songid(String id_songs, String page, String index) {
        String Service = "singer_detail_comment_service";
        String Provider = "default";
        String ParamSize = "6";
        String P1 = id_songs;
        String P2 = "401";
        String P3 = "DESC";
        String P4 = page;
        String P5 = index;

        apiSevice.getComment(new CallbackData<Comment>() {
            @Override
            public void onGetDataSuccess(ArrayList<Comment> arrayList) {
                Log.i("bac", "" + arrayList.size());
                fragmentDetailBuySongs.showComment(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Comment Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4, P5, Constant.USER_ID);
    }

    @Override
    public void addItemtoMyList(String sesionID, String msisdn, String expiration, String content_id) {
        String Service = "ADD_ITEM_TO_MYLIST";
        String Provider = "default";
        String ParamSize = "4";

        apiSevice.addItemToMyList(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size() > 0) {
                    fragmentDetailBuySongs.ShowBuySongsSuccess(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {
                //   fragmentDetailBuySongs.ShowBuySongsSuccess(Object);
                // fragmentDetailBuySongs.showMessage("Bạn mua thành công bài hát có thời hạn sử dụng "+Object.getExpiration() +" ngày");
            }
        }, Service, Provider, ParamSize, sesionID, msisdn, expiration, content_id);
    }

    @Override
    public void addGifttoPlayList(String sesionID, String msisdn, String phone_gift,
                                  String expiration, String content_id) {
        String Service = "ADD_GIFT_TO_PLAYLIST";
        String Provider = "default";
        String ParamSize = "5";

        apiSevice.addGiftToPlayList(new CallbackData<String>() {
            @Override
            public void onGetDataSuccess(ArrayList<String> arrayList) {
                if (arrayList.size() > 0) {
                    fragmentDetailBuySongs.showGiftSongsSuccess(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(String Object) {

            }
        }, Service, Provider, ParamSize, sesionID, msisdn, phone_gift, expiration, content_id);
    }

    @Override
    public void add_comment_services(String pid_id, String pobject_id, String pobject_type_id, String pcreate_date,
                                     String pis_public, String pcontent, String pname, String pchannel, String pauthor,
                                     String pparent_object_id, String pUserId, String pUserIp) {
        String Service = "comment_services";
        String Provider = "default";
        String ParamSize = "12";
        apiSevice.addComment(new CallbackData<String>() {
                                 @Override
                                 public void onGetDataSuccess(ArrayList<String> arrayList) {

                                 }

                                 @Override
                                 public void onGetDataFault(Exception e) {

                                 }

                                 @Override
                                 public void onGetObjectDataSuccess(String Object) {
                                     fragmentDetailBuySongs.show_add_comment_success(Object);
                                 }
                             }, Service, Provider, ParamSize, pid_id, pobject_id, pobject_type_id, pcreate_date, pis_public,
                pcontent, pname, pchannel, pauthor, pparent_object_id, pUserId, pUserIp);
    }

    @Override
    public void getBySinger(String id, String type, String index, String nuber) {
        String Service = "singer_detail_main_service";
        String Provider = "default";
        String ParamSize = "5";
        String P1 = id;
        String P2 = type;
        String P3 = index;
        String P4 = nuber;

        apiSevice.getRingtunes_ByAlbum(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                //viewRingtunes.showSongsBySingerId(arrayList);
                if (arrayList.size() > 0) {
                    Log.i("abc", arrayList.size() + "");
                    fragmentDetailBuySongs.show_lis_songs_bysinger(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3, P4);
    }

    public void api_suggestion_play(String Singer_id, String song_id, String UserID) {

        String Service = "suggestion_play";
        String Provider = "default";
        String ParamSize = "3";


        apiSevice.api_suggestion_play(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                //viewRingtunes.showSongsBySingerId(arrayList);
                if (arrayList.size() > 0) {
                    Log.i("abc", arrayList.size() + "");
                    fragmentDetailBuySongs.show_lis_songs_bysinger(arrayList);
                }
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize,Singer_id, song_id, UserID);
    }

    @Override
    public void getSearch(String key, String page, String index) {
        String Service = "search_main_service";
        String Provider = "default";
        String ParamSize = "4";
        String P1 = key;
        String P2 = page;
        String P3 = index;

        apiSevice.getSearchRingtunes(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentDetailBuySongs.show_lis_songs_bysinger(arrayList);
                //viewSearch.showListSearch(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, P1, P2, P3);
    }

    @Override
    public void get_info_songs_by_id(String id, String userID) {
        fragmentDetailBuySongs.showDialogLoading();
        String Service = "afp_service";
        String Provider = "default";
        String ParamSize = "2";

        apiSevice.get_info_songs_collection(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentDetailBuySongs.hideDialogLoading();
                fragmentDetailBuySongs.show_lis_songs_by_id(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentDetailBuySongs.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {
                fragmentDetailBuySongs.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, id, userID);
    }


}
