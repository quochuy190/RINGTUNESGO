package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.media.CRBTModel.CLI;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.Fragment.Collection.PresenterConllection;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.TopicViewHoder> {
    private List<GROUP> listItems;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;
    public int row_index;


    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterGroups(List<GROUP> items, Context context) {
        this.listItems = items;
        this.context = context;

    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_groups, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final GROUP item = listItems.get(position);
        if (position == listItems.size() - 1)
            holder.view_bottom.setVisibility(View.GONE);
        else holder.view_bottom.setVisibility(View.VISIBLE);
        List<CLI> list = new ArrayList<>();
        //String urlImage = IMAGE_URL + ringtunes.getImage_file();
        //Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        holder.txtGroupsName.setText(item.getName());
        if (item.getClis() != null) {
            list = item.getClis().getCLI();
            String phone = "";
            for (int i = 0; i < list.size(); i++) {
                phone = phone + list.get(i).getCaller_id() + ",";
                holder.txtGroupsPhones.setText("SĐT: " + phone);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.view_bottom)
        View view_bottom;
        @BindView(R.id.txt_item_groups_name)
        TextView txtGroupsName;
        @BindView(R.id.txt_item_groups_phones)
        TextView txtGroupsPhones;

        @BindView(R.id.item_img_avata)
        CircleImageView avata;
        @BindView(R.id.relative_group)
        RelativeLayout relative_group;

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
