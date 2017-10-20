package com.neo.media.Fragment.DetailSongs.Presenter;

/**
 * Created by QQ on 7/9/2017.
 */

public interface Presenter_Detail_RingtunesImpl {
    void getByTopic(String id, String page, String number);
    void getByAlbum(String id, String type, String index, String nuber);
    void getByType(String id, String type, String index, String nuber);
    void getBySinger(String id, String type, String index, String nuber);
    void log_Info_Charge_Server(String P1, String P2, String P3, String P4, String P5);
    void getByRingtunesHot(String top_type, String page, String index);
    void getByRingtunesNew(String top_type, String page, String index);


}
