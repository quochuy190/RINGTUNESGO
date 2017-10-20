package com.neo.media.Fragment.Singer.Presenter;

import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.Singer;

import java.util.List;

/**
 * Created by QQ on 7/8/2017.
 */

public interface PresenterSingerImpl {
    interface Presenter{
        void getAllSinger(String page, String index);
        void getDetailSinger(String singer_id);
        void getSongsBySinger(String singer_id, String page, String index);
    }
    interface View{
        void showSingerDetail(List<Singer> lisSinger);
        void showSongbySinger(List<Ringtunes> lisRing);
    }

    void getDetail_Singer(String Singer_id);
}
