package com.neo.media.Fragment.Home.View;

import com.neo.media.Model.Banner;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;
import com.neo.media.Model.Topic;
import com.neo.media.Model.Type;

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
