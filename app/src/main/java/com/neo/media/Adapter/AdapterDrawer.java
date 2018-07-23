/*
package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.MainNavigationActivity;
import com.neo.media.Model.DrawerItem;
import com.neo.media.R;

import java.util.List;

*/
/**
 * Created by QQ on 8/21/2017.
 *//*


public class AdapterDrawer extends ArrayAdapter<DrawerItem> {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public AdapterDrawer(Context context, int layoutResourceID,
                         List<DrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        DrawerTitleHolder drawerTitleHolder;
        DrawerItemHolder drawerHolder;
        View view = convertView;
        if (!MainNavigationActivity.isLogin) {
            if (position == 0) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerTitleHolder = new DrawerTitleHolder();
                view = inflater.inflate(R.layout.item_title_drawer, parent, false);
                drawerTitleHolder.ItemName = (TextView) view.findViewById(R.id.txt_title_drawer);
                drawerTitleHolder.ItemName.setText("RingTunes");

            } else {
                if (view == null) {
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    drawerHolder = new DrawerItemHolder();

                    view = inflater.inflate(R.layout.item_drawer, parent, false);
                    drawerHolder.ItemName = (TextView) view
                            .findViewById(R.id.txt_item_drawer);
                    drawerHolder.icon = (ImageView) view.findViewById(R.id.img_item_drawer);

                    view.setTag(drawerHolder);

                } else {
                    drawerHolder = (DrawerItemHolder) view.getTag();

                }
                DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
                if (dItem.getImgResID() != 0) {
                    drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                            dItem.getImgResID()));
                }
                if (dItem.getItemName().length() > 0)
                    drawerHolder.ItemName.setText(dItem.getItemName());
            }
        } else {
            if (position == 0) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerTitleHolder = new DrawerTitleHolder();
                view = inflater.inflate(R.layout.item_title_drawer, parent, false);
                drawerTitleHolder.ItemName = (TextView) view.findViewById(R.id.txt_title_drawer);
                drawerTitleHolder.ItemName.setText("RingTunes");

            } else if (position == 5) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerTitleHolder = new DrawerTitleHolder();
                view = inflater.inflate(R.layout.item_title_drawer, parent, false);
                drawerTitleHolder.ItemName = (TextView) view.findViewById(R.id.txt_title_drawer);
                drawerTitleHolder.ItemName.setText("Trang cá nhân");
            } else {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerHolder = new DrawerItemHolder();

                view = inflater.inflate(R.layout.item_drawer, parent, false);
                drawerHolder.ItemName = (TextView) view
                        .findViewById(R.id.txt_item_drawer);
                drawerHolder.icon = (ImageView) view.findViewById(R.id.img_item_drawer);

                view.setTag(drawerHolder);

                DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
                if (dItem.getImgResID() != 0) {
                    drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                            dItem.getImgResID()));
                }
                if (dItem.getItemName().length() > 0)
                    drawerHolder.ItemName.setText(dItem.getItemName());
            }
        }


        return view;
    }

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
    }

    private static class DrawerTitleHolder {
        TextView ItemName;
    }
}
*/
