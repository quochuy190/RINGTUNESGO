package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Fragment.CaNhan.Collection.FragmentConllection;
import com.neo.media.Fragment.CaNhan.Collection.PresenterConllection;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.neo.media.Config.Constant.IMAGE_URL;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterCollection extends RecyclerView.Adapter<AdapterCollection.TopicViewHoder> {
    private List<Item> listItems;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;


    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterCollection(List<Item> items, Context context, PresenterConllection presenterConllection) {
        this.listItems = items;
        this.context = context;
        this.presenterConllection = presenterConllection;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_collection, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        final Item item = listItems.get(position);
        if (item.getImage_url() != null) {
            String urlImage = IMAGE_URL + item.getImage_url();
            Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        }
        holder.txtRingtunescName.setText(item.getContent_title());
        holder.txtRingtunesSinger.setText(item.getContent_artist());
        holder.txtRingtunesCounter.setText("HSD :" + item.getExpiration_date());
        holder.txtRingtunesPrice.setText("" + item.getContent_billing_desc());
        if (item.is_check) {
            holder.img_play_item_ringtunes.setVisibility(View.VISIBLE);
        } else holder.img_play_item_ringtunes.setVisibility(View.GONE);
        holder.img_play_item_ringtunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentConllection.deleteItem(context, item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_img_collection)
        CircleImageView imgRingtunes;
        @BindView(R.id.item_name_collection)
        TextView txtRingtunescName;
        @BindView(R.id.item_singer_collection)
        TextView txtRingtunesSinger;
        /*        @BindView(R.id.item_code_ringtunes)
                TextView txtRingtunesCode;*/
        @BindView(R.id.item_time_collection)
        TextView txtRingtunesCounter;
        @BindView(R.id.item_price_collection)
        TextView txtRingtunesPrice;
        @BindView(R.id.img_play_item_ringtunes)
        ImageView img_play_item_ringtunes;

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
