package com.neo.ringtunesgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neo.ringtunesgo.Model.Topic;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterSlogan extends RecyclerView.Adapter<AdapterSlogan.TopicViewHoder> {
    private List<Topic> listTopic;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterSlogan(List<Topic> listTopic, Context context) {
        this.listTopic = listTopic;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slogan, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        String urlImage = "";
        String urlBigImage="";
        final Topic topic = listTopic.get(position);
        if (topic.getNAME()!=null)
            holder.txt_name_slogan.setText(topic.getNAME());
        if (topic.getDescription()!=null)
            holder.txt_depcrition_slogan.setText(topic.getDescription());
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_slogan)
        TextView txt_name_slogan;
        @BindView(R.id.txt_depcrition_slogan)
        TextView txt_depcrition_slogan;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.OnItemClickListener(getLayoutPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
