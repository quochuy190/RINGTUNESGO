package com.neo.media.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.Model.Ringtunes;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListSongPlayer extends RecyclerView.Adapter<AdapterListSongPlayer.TopicViewHoder> {
    private List<Ringtunes> listRingtunes;
    private Context context;
    private setOnItemClickListener setOnItemClickListener;
    public boolean isPlay = false;

    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterListSongPlayer(List<Ringtunes> listRingtunes, Context context) {
        this.listRingtunes = listRingtunes;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_songs_player, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final Ringtunes ringtunes = listRingtunes.get(position);
        if (ringtunes.isPlaying()){
          //  holder.img_list_song_player.setImageDrawable(context.getResources().getDrawable(R.drawable.notnhac));
            //holder.txt_name_lis_song.setText(ringtunes.getProduct_name());
            holder.txt_name_lis_song.setTextColor(context.getResources().getColor(R.color.orange));
            holder.txt_namesinger_lis_song.setTextColor(context.getResources().getColor(R.color.orange));
        }else {
            holder.txt_name_lis_song.setTextColor(context.getResources().getColor(R.color.text_home));
            holder.txt_namesinger_lis_song.setTextColor(context.getResources().getColor(R.color.text_home));
        }
        if (ringtunes.getProduct_name() != null && ringtunes.getProduct_name().length() > 0) {
            holder.txt_name_lis_song.setText(ringtunes.getProduct_name());
        }
        if (ringtunes.getSinger_name() != null && ringtunes.getSinger_name().length() > 0) {
            holder.txt_namesinger_lis_song.setText(ringtunes.getSinger_name());
        }

    }

    @Override
    public int getItemCount() {
        return listRingtunes.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_name_lis_song)
        TextView txt_name_lis_song;
        @BindView(R.id.txt_namesinger_lis_song)
        TextView txt_namesinger_lis_song;
        @BindView(R.id.img_list_song_player)
        ImageView img_list_song_player;
        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

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
