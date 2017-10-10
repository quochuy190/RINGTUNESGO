package com.neo.ringtunesgo.Fragment.DetailSongs.View;

import com.neo.ringtunesgo.Model.Ringtunes;

import java.util.List;

/**
 * Created by QQ on 7/9/2017.
 */

public interface View_RingtunesImpl {
    void showListRingtunes(List<Ringtunes> listRingtunes);
    void showBacgroup(String urlImage);
    void showMessage(String message);
}
