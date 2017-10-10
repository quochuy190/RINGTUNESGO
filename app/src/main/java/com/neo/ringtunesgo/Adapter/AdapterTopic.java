package com.neo.ringtunesgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.ringtunesgo.Model.Topic;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.CustomUtils;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neo.ringtunesgo.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterTopic extends RecyclerView.Adapter<AdapterTopic.TopicViewHoder>{
    private List<Topic> listTopic;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterTopic(List<Topic> listTopic, Context context) {
        this.listTopic = listTopic;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home,parent,false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        final Topic topic = listTopic.get(position);
        String urlImage = IMAGE_URL + topic.getPhoto();
        Glide.with(context).load(urlImage).into(holder.imgTocpicPhoto);
        holder.txtTocpicName.setText(topic.getPackage_name());
        String hit = CustomUtils.conventNumber(topic.getHits());
        holder.txtHome_Counter.setText(topic.getCounter()+" bài");
        holder.txtHome_Hits.setText(hit+" lượt tải");

      //  holder.txtHome_Hits.setText(hit+" lượt tải");

       /* holder.imgTocpicPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Detail_Ringtunes.class);
                intent.putExtra("id", topic.getId());
                intent.putExtra("id_type", Config.TOPIC);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.item_home_photo)
        ImageView imgTocpicPhoto;
        @BindView(R.id.item_home_name)
        TextView txtTocpicName;
        @BindView(R.id.item_counter_home)
        TextView txtHome_Counter;
        @BindView(R.id.item_hits_home)
        TextView txtHome_Hits;
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
