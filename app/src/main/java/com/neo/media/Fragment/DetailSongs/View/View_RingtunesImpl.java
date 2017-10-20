package com.neo.media.Fragment.DetailSongs.View;

import com.neo.media.Model.Ringtunes;

import java.util.List;

/**
 * Created by QQ on 7/9/2017.
 */

public interface View_RingtunesImpl {
    void showListRingtunes(List<Ringtunes> listRingtunes);
    void showBacgroup(String urlImage);
    void showMessage(String message);
}
