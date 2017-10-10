package com.neo.ringtunesgo.untils;

import com.neo.ringtunesgo.Model.PlayList;

/**
 * Created by baoan123 on 12/30/16.
 */
public class PlaySongEvent {
    PlayList song;
    int current;
    int type; // 0 ring  1 music


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public PlaySongEvent(PlayList p0, int current, int type) {
        this.song = p0;
        this.current = current;
        this.type=type;
    }

    public int getType() {
        return type;
    }

    public PlayList getSong() {
        return song;
    }
}
