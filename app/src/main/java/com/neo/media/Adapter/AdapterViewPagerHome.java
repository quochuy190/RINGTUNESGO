package com.neo.media.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.neo.media.Config.Constant;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;


/**
 * Created by QQ on 2/20/2017.
 */

public class AdapterViewPagerHome extends PagerAdapter {
    Context context;
    int resorce;
    List<String> list;
    private setOnItemClickListener setOnItemClickListener;

    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterViewPagerHome(List<String> list, int resorce, Context context) {
        this.list = list;
        this.resorce = resorce;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView ;
        LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        View itemView = layoutInflater.inflate(resorce, container, false);

        imageView = (ImageView) itemView.findViewById(R.id.imgviewpager);
        //imgView.setImageResource(m_objImageList.get(position).getiImage());
        Glide.with(context).load(Constant.IMAGE_URL +list.get(position)).into(imageView);
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnItemClickListener.OnItemClickListener(position);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }
}
