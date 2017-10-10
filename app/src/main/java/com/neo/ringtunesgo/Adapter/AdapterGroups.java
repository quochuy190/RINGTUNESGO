package com.neo.ringtunesgo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.ringtunesgo.CRBTModel.CLI;
import com.neo.ringtunesgo.CRBTModel.GROUP;
import com.neo.ringtunesgo.Fragment.Collection.PresenterConllection;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.TopicViewHoder>  {
    private List<GROUP> listItems;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;
    public int row_index;


    public com.neo.ringtunesgo.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.ringtunesgo.untils.setOnItemClickListener setOnItemClickListener) {
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
        List<CLI> list = new ArrayList<>();
        //String urlImage = IMAGE_URL + ringtunes.getImage_file();
        //Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        holder.txtGroupsName.setText(item.getName());
        list = item.getClis().getCLI();
        String phone = "";
        for (int i = 0;i<list.size();i++){
            phone = phone + list.get(i).getCaller_id()+",";
        }
        holder.txtGroupsPhones.setText(phone);
        final List<CLI> finalLis = new ArrayList<>();
        finalLis.addAll(list);
       /* holder.relative_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index= position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.relative_group.setBackgroundColor(R.color.purple);

        }
        else
        {
            holder.relative_group.setBackgroundColor(R.color.background_item);
        }*/

/*
        holder.item_img_group_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", item.getName());
                bundle.putSerializable("lis_member", (Serializable) finalLis);
                FragmentUtil.addFragmentData(MainNavigationActivity.fragmentActivity, FragmentGroupMember.getInstance(), true, bundle);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

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
