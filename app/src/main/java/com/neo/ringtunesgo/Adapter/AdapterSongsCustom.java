package com.neo.ringtunesgo.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.CustomUtils;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.neo.ringtunesgo.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 8/1/2017.
 */

public class AdapterSongsCustom extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<Ringtunes> lisSongs;
    String img_Songs_Detail;
    setOnItemClickListener setOnItemClickListener;
    public static MediaPlayer mp;
    public boolean isPlay = false;

    public com.neo.ringtunesgo.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.ringtunesgo.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterSongsCustom(Context mContext, List<Ringtunes> lisSongs, String img_Songs_Detail) {
        this.mContext = mContext;
        this.lisSongs = lisSongs;
        this.img_Songs_Detail = img_Songs_Detail;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else return 1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_img_songs_detail, parent, false);
                return new ViewHolder0(view);
            case 1:
                View view_two = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.item_ringtunes, parent, false);
                return new CustomSongs(view_two);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Ringtunes songs = lisSongs.get(position);
        if (position == 0) {
            ViewHolder0 viewHolder0 = (ViewHolder0) holder;
            if (songs.getImage_file() != null){
                String urlImage = IMAGE_URL + songs.getImage_file();
                Glide.with(mContext).load(urlImage).into(viewHolder0.item_img_songs_detail);
            }
        } else {
            final CustomSongs customSongs = (CustomSongs) holder;

            if (songs.getImage_file() != null) {
                String urlImage = IMAGE_URL + songs.getImage_file();
                Glide.with(mContext).load(urlImage).into(customSongs.imgRingtunes);
            } else {
                Glide.with(mContext).load(R.mipmap.ringtunes_final).into(customSongs.imgRingtunes);
            }
            if (songs.getProduct_name() != null) {
                customSongs.txtRingtunescName.setText(songs.getProduct_name());
            }
            if (songs.getSinger_name() != null) {
                customSongs.txtRingtunesSinger.setText(songs.getSinger_name());
            }
            if (songs.getHist() != null) {
                String hit = CustomUtils.conventNumber(songs.getHist());
                customSongs.txtRingtunesCounter.setText("Lượt tải: " + hit);

            }
            if (songs.getPrice()!=null){
                customSongs.txtRingtunesPrice.setText("Giá: " + songs.getPrice());
            }

        }
    }

    @Override
    public int getItemCount() {
        return lisSongs.size();
    }

    public class CustomSongs extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_img_ringtunes)
        CircleImageView imgRingtunes;
        @BindView(R.id.item_name_ringtunes)
        TextView txtRingtunescName;
        @BindView(R.id.item_singer_ringtunes)
        TextView txtRingtunesSinger;
        @BindView(R.id.item_counter_ringtunes)
        TextView txtRingtunesCounter;
        @BindView(R.id.item_price_ringtunes)
        TextView txtRingtunesPrice;
        @BindView(R.id.img_play_item_ringtunes)
        ImageView img_play;
      /*  @BindView(R.id.img_gif_item_ringtunes)
        ImageView img_Gif;*/


        public CustomSongs(View itemView1) {
            super(itemView1);
            ButterKnife.bind(this, itemView1);
            itemView1.setOnClickListener(this);
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

    class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img_songs_detail)
        ImageView item_img_songs_detail;

        public ViewHolder0(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
