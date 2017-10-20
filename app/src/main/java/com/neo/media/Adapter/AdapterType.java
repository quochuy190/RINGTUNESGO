package com.neo.media.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.Model.Type;
import com.neo.media.R;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.neo.media.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterType extends RecyclerView.Adapter<AdapterType.TopicViewHoder>{
    private List<Type> listSinger;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterType(List<Type> listSinger, Context context) {
        this.listSinger = listSinger;
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
        final Type topic = listSinger.get(position);
        if (topic.getThumbnal_image()!=null){
            String urlImage = IMAGE_URL + topic.getThumbnal_image();
            Glide.with(context).load(urlImage).into(holder.imgTocpicPhoto);
        }else {
            Glide.with(context).load(R.drawable.ringtunes_go_banner).into(holder.imgTocpicPhoto);
        }
        if (topic.getName()!=null){
            holder.txtTocpicName.setText(topic.getName());
        }
        String hit = CustomUtils.conventNumber(topic.getHits());
        if (topic.getHits()!=null){
            holder.txtHome_Hits.setText(hit+" lượt tải");
        }
        if (topic.getCount()!=null){
            holder.txtHome_Counter.setText(topic.getCount()+" bài");
        }
       /* holder.txtHome_Counter.setText(topic.getCount()+" bài");
        String hit = CustomUtils.conventNumber(topic.getHits());
        holder.txtHome_Hits.setText(hit+" lượt tải");*/

      /*  holder.imgSingerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Detail_Ringtunes.class);
                intent.putExtra("id", topic.getId());
                intent.putExtra("id_type", Config.TYPE);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listSinger.size();
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
            OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }
}
