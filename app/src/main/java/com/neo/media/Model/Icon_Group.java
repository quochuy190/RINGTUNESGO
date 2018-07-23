package com.neo.media.Model;

/**
 * Created by QQ on 11/28/2017.
 */

public class Icon_Group {
    private int img_icon;
    private boolean isCheck;
    private int icon_background;

    public Icon_Group(int img_icon, boolean isCheck, int icon_background) {
        this.img_icon = img_icon;
        this.isCheck = isCheck;
        this.icon_background = icon_background;
    }

    public Icon_Group() {
    }

    public int getIcon_background() {
        return icon_background;
    }

    public void setIcon_background(int icon_background) {
        this.icon_background = icon_background;
    }

    public int getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(int img_icon) {
        this.img_icon = img_icon;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
