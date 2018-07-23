package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.neo.media.Fragment.CaNhan.Collection.PresenterConllection;
import com.neo.media.Model.Icon_Group;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterIcon_Group extends RecyclerView.Adapter<AdapterIcon_Group.TopicViewHoder> {
    private List<Icon_Group> lisItem;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;


    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterIcon_Group( List<Icon_Group> items, Context context) {
        this.lisItem = items;
        this.context = context;

    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_icon_backgroup_group, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, final int position) {
        final Icon_Group item = lisItem.get(position);
        holder.icon_group_background.setImageResource(item.getImg_icon());
        if (item.isCheck()) {
            holder.icon_chose_group.setVisibility(View.VISIBLE);
        }else   holder.icon_chose_group.setVisibility(View.GONE);
      /*if (item.is_check){
          holder.cb_conllection.setChecked(true);
      }else holder.cb_conllection.setChecked(false);*/


    }

    @Override
    public int getItemCount() {
        return lisItem.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.icon_group_background)
        ImageView icon_group_background;
        @BindView(R.id.icon_chose_group)
        ImageView icon_chose_group;
        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            setOnItemClickListener.OnItemClickListener(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            setOnItemClickListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }
}
