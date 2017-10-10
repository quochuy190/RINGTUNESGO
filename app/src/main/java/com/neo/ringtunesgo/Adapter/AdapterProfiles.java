package com.neo.ringtunesgo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.Fragment.Collection.PresenterConllection;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterProfiles extends RecyclerView.Adapter<AdapterProfiles.TopicViewHoder> {
    private List<PROFILE> listItems;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;
    boolean isVisible = false;

    public com.neo.ringtunesgo.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.ringtunesgo.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterProfiles(List<PROFILE> items, Context context) {
        this.listItems = items;
        this.context = context;

    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, int position) {
        final PROFILE item = listItems.get(position);
        //String urlImage = IMAGE_URL + ringtunes.getImage_file();
        //Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        if (item.getCaller_type().equals("DEFAULT")) {
            holder.txtProfileName.setText("Tất cả số gọi đến");
        } else if (item.getCaller_type().equals("CLI")) {
            holder.txtProfileName.setText("Số gọi đến: " + item.getCaller_id());
        } else if (item.getCaller_type().equals("GROUP")) {
            if (item.getCaller_name() != null)
                holder.txtProfileName.setText("Nhóm: " + item.getCaller_name());
            else
                holder.txtProfileName.setText("Nhóm: " + item.getCaller_id());
        }
        //holder.txtProfileName.setText(item.getCaller_id());
        if (item.getContent_entity().equals("TRACK")) {
            holder.txt_setup_type.setText("1 bài hát");
        } else if (item.getContent_entity().equals("MYLIST")) {
            holder.txt_setup_type.setText("ngẫu nhiên");
        } else if (item.getContent_entity().equals("NONE")) {
            holder.txt_setup_type.setText("Phát mặc định");
        }
        if (item.getContent_entity().equals("TRACK")) {
            holder.txt_setup_number_songs.setText("" + item.getContent_title());
        } else if (item.getContent_entity().equals("MYLIST")) {
            holder.txt_setup_number_songs.setText("ALL");
        } else if (item.getContent_entity().equals("NONE")) {
            holder.txt_setup_number_songs.setText("Nhạc chuông mặc định");
        }
        if (item.getTime_category().equals("-1")) {
            holder.txt_setup_time.setText("Cả ngày");
        } else {
            holder.txt_setup_time.setText(item.getTimeBase().getTime_category_4().getFrom_time() + " - "
                    + item.getTimeBase().getTime_category_4().getTo_time());
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.name_profiles)
        TextView txtProfileName;
        @BindView(R.id.profile_setup)
        TextView txt_setup_type;
        @BindView(R.id.profile_random)
        TextView txt_setup_time;
        @BindView(R.id.profile_number_songs)
        TextView txt_setup_number_songs;
        @BindView(R.id.txt_edit_profile)
        TextView txt_edit_profile;
        @BindView(R.id.txt_delete_profile)
        TextView txt_delete_profile;
        @BindView(R.id.img_dot_profile)
        ImageView img_dot_profile;
        @BindView(R.id.linner_edit)
        LinearLayout linner_edit;
       /* @BindView(R.id.item_img_dot_menu)
        ImageView img_dot_menu;*/

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
