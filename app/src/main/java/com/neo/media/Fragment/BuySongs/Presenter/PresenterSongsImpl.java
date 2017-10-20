package com.neo.media.Fragment.BuySongs.Presenter;

import com.neo.media.CRBTModel.Item;
import com.neo.media.Model.Comment;
import com.neo.media.Model.Ringtunes;

import java.util.List;

/**
 * Created by QQ on 7/7/2017.
 */

public interface PresenterSongsImpl {
    interface Presenter {
        void login(String username, String pass);
        void log_Info_Charge_Server(String P1, String P2, String P3, String P4, String P5, String p6,String P7, String P8);

        void getSongsInformation(String sesionID, String msisdn, String content_id);

        void getComment_by_Songid(String id_songs, String page, String index);

        void addItemtoMyList(String sesionID, String msisdn, String expiration, String content_id);

        void addGifttoPlayList(String sesionID, String msisdn, String phone_gift,String expiration, String content_id);

        void add_comment_services(String pid_id, String pobject_id, String pobject_type_id, String pcreate_date,
                                  String pis_public,String pcontent, String pname, String pchannel, String pauthor,
                                  String pparent_object_id, String pUserId, String pUserIp);
        void getBySinger(String id, String type, String index, String nuber);
        void getSearch(String key, String page, String index);
        void get_info_songs_by_id(String id, String userID);
    }

    interface View {
        void showGiftSongsSuccess(List<String> list);

        void ShowBuySongsSuccess(List<String> list);

        void ShowItems(Item items);

        void showComment(List<Comment> listComment);

        void showMessage(String message);
        void show_add_comment_success(String s);
        void show_lis_songs_bysinger(List<Ringtunes> lisRingtunes);
        void show_lis_songs_by_id(List<Ringtunes> lisSongs);
    }

}
