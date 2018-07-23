package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.Model.Ringtunes;
import com.neo.media.R;
import com.neo.media.untils.CustomUtils;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.neo.media.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterListSongs extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1, VIEW_TYPE_IMAGE_BANNER = 2;
    private List<Ringtunes> listRingtunes;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private String TAG;
    public static MediaPlayer mp;
    public boolean isPlay = false;

    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterListSongs(List<Ringtunes> listRingtunes, Context context) {
        this.listRingtunes = listRingtunes;
        this.context = context;
    }

    /*@Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
    *//*    View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ringtunes, parent, false);
        return new TopicViewHoder(view);*//*
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_ringtunes, parent, false);
            return new TopicViewHoder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }else if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        }
        return null;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_ringtunes, parent, false);
            return new TopicViewHoder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }else if (viewType == VIEW_TYPE_IMAGE_BANNER) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_image_banner, parent, false);
            return new ImageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holders, int position) {
        if (holders instanceof TopicViewHoder) {
            TopicViewHoder holder = (TopicViewHoder) holders;
            final Ringtunes ringtunes = listRingtunes.get(position);
            if (ringtunes.getImage_file() != null && ringtunes.getImage_file().length() > 0) {
                String urlImage = IMAGE_URL + ringtunes.getImage_file();
                Glide.with(context).load(urlImage).into(holder.imgRingtunes);
            } else {
                Glide.with(context).load(R.mipmap.ic_launcher_round).into(holder.imgRingtunes);
            }
            if (ringtunes.getProduct_name() != null && ringtunes.getProduct_name().length() > 0) {
                holder.txtRingtunescName.setText(ringtunes.getProduct_name().replace("u0027", "'"));
            }
            if (ringtunes.getSinger_name() != null && ringtunes.getSinger_name().length() > 0) {
                holder.txtRingtunesSinger.setText(ringtunes.getSinger_name());
            }
            if (ringtunes.getHist() != null && ringtunes.getHist().length() > 0) {
                String hit = CustomUtils.conventNumber(ringtunes.getHist());
                //holder.txtRingtunesCounter.setText(hit+" lượt tải");
                holder.txtRingtunesCounter.setText("Lượt tải: " + hit);
            } else if (ringtunes.getCOUNTER() != null) {
                String hit = CustomUtils.conventNumber(ringtunes.getCOUNTER());
                //holder.txtRingtunesCounter.setText(hit+" lượt tải");
                holder.txtRingtunesCounter.setText("Lượt tải: " + hit);
            } else
                holder.txtRingtunesCounter.setText("Lượt tải: " + "0");
            if (ringtunes.getPrice() != null && ringtunes.getPrice().length() > 0) {
                holder.txtRingtunesPrice.setText("Giá: " + ringtunes.getPrice());
            }
        } else if (holders instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holders;
            loadingViewHolder.progressBar.setIndeterminate(true);
        } else if (holders instanceof ImageViewHolder) {
            ImageViewHolder headerViewHolder = (ImageViewHolder) holders;
           // headerViewHolder.txt_Header.setText(mLisFlightInfo.get(position).getsHeader());
            if (listRingtunes.get(position).getImage_file() != null &&
                    listRingtunes.get(position).getImage_file().length() > 0) {
                String urlImage = IMAGE_URL + listRingtunes.get(position).getImage_file();

                Glide.with(context)
                        .load(urlImage)
                        /*.apply(new RequestOptions() *//*.override(Target.SIZE_ORIGINAL,
                                ((ImageViewHolder) holders).img_Banner.getLayoutParams().height)*//*
                                .placeholder(R.drawable.img_default)
                                .error(R.drawable.img_default))*/
                        .into(((ImageViewHolder) holders).img_Banner);


                //Glide.with(context).load(urlImage).into(headerViewHolder.img_Banner);
            } else {
                Glide.with(context).load(R.drawable.img_default).into(headerViewHolder.img_Banner);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // So sánh nếu item được get tại vị trí này là null thì view đó là loading view , ngược lại là item
        if (listRingtunes.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            if (position==0) {
                return VIEW_TYPE_IMAGE_BANNER;
            } else
                return VIEW_TYPE_ITEM;
        }
    }

    /*@Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final Ringtunes ringtunes = listRingtunes.get(position);
        if (ringtunes.getImage_file() != null && ringtunes.getImage_file().length() > 0) {
            String urlImage = IMAGE_URL + ringtunes.getImage_file();
            Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        } else {
            Glide.with(context).load(R.mipmap.ic_launcher_round).into(holder.imgRingtunes);
        }
        if (ringtunes.getProduct_name() != null && ringtunes.getProduct_name().length() > 0) {
            holder.txtRingtunescName.setText(ringtunes.getProduct_name().replace("u0027", "'"));
        }
        if (ringtunes.getSinger_name() != null && ringtunes.getSinger_name().length() > 0) {
            holder.txtRingtunesSinger.setText(ringtunes.getSinger_name());
        }
        if (ringtunes.getHist() != null && ringtunes.getHist().length() > 0) {
            String hit = CustomUtils.conventNumber(ringtunes.getHist());
            //holder.txtRingtunesCounter.setText(hit+" lượt tải");
            holder.txtRingtunesCounter.setText("Lượt tải: " + hit);
        } else if (ringtunes.getCOUNTER() != null) {
            String hit = CustomUtils.conventNumber(ringtunes.getCOUNTER());
            //holder.txtRingtunesCounter.setText(hit+" lượt tải");
            holder.txtRingtunesCounter.setText("Lượt tải: " + hit);
        } else
            holder.txtRingtunesCounter.setText("Lượt tải: " + "0");
        if (ringtunes.getPrice() != null && ringtunes.getPrice().length() > 0) {
            holder.txtRingtunesPrice.setText("Giá: " + ringtunes.getPrice());
        }

    }*/

    @Override
    public int getItemCount() {
        return listRingtunes.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_img_ringtunes)
        CircleImageView imgRingtunes;
        @BindView(R.id.item_name_ringtunes)
        TextView txtRingtunescName;
        @BindView(R.id.item_singer_ringtunes)
        TextView txtRingtunesSinger;
        /*        @BindView(R.id.item_code_ringtunes)
                TextView txtRingtunesCode;*/
        @BindView(R.id.item_counter_ringtunes)
        TextView txtRingtunesCounter;
        @BindView(R.id.item_price_ringtunes)
        TextView txtRingtunesPrice;
        @BindView(R.id.img_play_item_ringtunes)
        ImageView img_Download;


        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            img_Download.setOnClickListener(this);
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
    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_Banner;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            img_Banner = (ImageView) itemView.findViewById(R.id.img_banner);
        }
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
}
