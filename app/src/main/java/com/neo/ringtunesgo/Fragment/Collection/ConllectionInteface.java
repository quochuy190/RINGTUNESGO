package com.neo.ringtunesgo.Fragment.Collection;

import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.Model.Ringtunes;

import java.util.List;

/**
 * Created by QQ on 7/24/2017.
 */

public interface ConllectionInteface {
    interface Presenter{
        void getConllection(String sesionID,String msisdn);
        void deleteConllection(String sesionID, String msisdn, String item_id, String userId);
        void getSongsSame(String idSinger, String page, String index);
        void get_info_songs_collection(String id, String userID);
    }
    interface View{
        void showLisSongsSame(List<Ringtunes> lisRing);
        void showConllection(List<Item> listItems);
        void showDeleteSuccessfull(List<String> list);
        void show_list_songs_collection(List<Ringtunes> listSongs);
    }
}
