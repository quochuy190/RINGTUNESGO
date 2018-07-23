package com.neo.media.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.Model.Canhan;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterCanhan extends RecyclerView.Adapter<AdapterCanhan.TopicViewHoder>{
    private List<Canhan> listTopic;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterCanhan(List<Canhan> listTopic, Context context) {
        this.listTopic = listTopic;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_canhan,parent,false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        final Canhan obj = listTopic.get(position);
        holder.img_icon_canhan.setImageResource(obj.getImg_photo());
        holder.txt_name_canhan.setText(obj.getName());

    /*    holder.imgTocpicPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", Config.ALBUM);
                bundle.putString("id", topic.getId());
                FragmentUtil.addFragmentData(MainActivity.fragmentActivity, FragmentSongs.getInstance(), true, bundle);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.img_icon_canhan)
        ImageView img_icon_canhan;
        @BindView(R.id.txt_name_canhan)
        TextView txt_name_canhan;

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
