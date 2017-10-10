package com.neo.ringtunesgo.untils;

import android.content.Context;

import net.grandcentrix.tray.AppPreferences;

/**
 * Created by Administrator on 11/24/2016.
 */
public class Storage extends AppPreferences {
    //player ringtunes
    public static final String IS_PLAYING = "IS_PLAYING";
    //id Singer
    public static final String ID_SINGER = "ID_SINGER";

    public Storage(Context context) {
        super(context);
    }

}
