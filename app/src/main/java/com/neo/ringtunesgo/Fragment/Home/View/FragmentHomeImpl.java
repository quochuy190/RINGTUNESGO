package com.neo.ringtunesgo.Fragment.Home.View;

import com.neo.ringtunesgo.Model.Banner;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Model.Singer;
import com.neo.ringtunesgo.Model.Topic;
import com.neo.ringtunesgo.Model.Type;

import java.util.List;

/**
 * Created by QQ on 7/7/2017.
 */

public interface FragmentHomeImpl {

    void showLoading();

    void hideLoading();

    void loadDataErorr();

    void showViewpager(List<String> list);

    void showListTopic(List<Topic> listTopic);
   // void showListAlbum(List<Album> listAlbum);
    void showListRingtunesHot(List<Ringtunes> lisRingtunHot);
    void showListRingtunesNew(List<Ringtunes> lisRingtunNew);
    void showListSinger(List<Singer> listSinger);
    void showListType(List<Type> listType);
    void showlistBanner(List<Banner> lisBanner);

}
