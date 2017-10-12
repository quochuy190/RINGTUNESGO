package com.neo.ringtunesgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.ringtunesgo.Model.Topic;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neo.ringtunesgo.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterEventHome extends RecyclerView.Adapter<AdapterEventHome.TopicViewHoder> {
    private List<Topic> listTopic;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterEventHome(List<Topic> listTopic, Context context) {
        this.listTopic = listTopic;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout
                .LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        String urlImage = "";
        String urlBigImage="";
        final Topic topic = listTopic.get(position);
        if (topic.getPhoto() != null){
            urlImage = IMAGE_URL + topic.getPhoto();
        }
        if (topic.getIMAGE()!=null){
            urlImage = IMAGE_URL + topic.getTHUMBNAIL_IMAGE();
        }
        if (topic.getBigphoto() != null){
            urlBigImage = IMAGE_URL + topic.getBigphoto();
        }
        if (topic.getTHUMBNAIL_IMAGE()!=null){
            urlBigImage = IMAGE_URL + topic.getIMAGE();
        }
        Glide.with(context).load(urlImage).into(holder.img_event_photo);
        Glide.with(context).load(urlBigImage).into(holder.img_photo_event_background);

        if (topic.getPackage_name() != null)
            holder.txt_name_event.setText(topic.getPackage_name());

    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.img_photo_event_background)
        ImageView img_photo_event_background;
        @BindView(R.id.img_event_photo)
        ImageView img_event_photo;
        @BindView(R.id.txt_name_event)
        TextView txt_name_event;
        @BindView(R.id.relative_event_all)
        RelativeLayout relative_event_all;

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
