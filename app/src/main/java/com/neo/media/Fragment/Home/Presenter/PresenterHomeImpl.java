package com.neo.media.Fragment.Home.Presenter;

/**
 * Created by QQ on 7/7/2017.
 */

public interface PresenterHomeImpl  {

    void getLisTopoc(String P1, String P2);
   // void getLisAlbum(String P1, String P2);
    void getRingtunesHot(String P1, String P2);
    void getRingtunesNew(String P1, String P2);
    void getListSinger(String P1, String P2);
    void getListType(String P1, String P2);
    void getbanner_header(String userid);
    void getrankRingtunes(String index, String number, String userID);
}
