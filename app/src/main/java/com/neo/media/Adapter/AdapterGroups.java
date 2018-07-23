package com.neo.media.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.CRBTModel.GROUP;
import com.neo.media.Fragment.CaNhan.Collection.PresenterConllection;
import com.neo.media.R;
import com.neo.media.untils.PhoneNumber;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterGroups extends RecyclerView.Adapter<AdapterGroups.TopicViewHoder> {
    private List<GROUP> listItems;
    private Context context;
    private Activity activity;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;
    public int row_index;


    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public AdapterGroups(List<GROUP> items, Context context) {
        this.listItems = items;
        this.context = context;

    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nhom, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final GROUP item = listItems.get(position);
        if (item.isIs_add_group()) {
            holder.txt_name_group.setVisibility(View.GONE);
            holder.txt_phone_group.setVisibility(View.GONE);
            holder.txt_tennhom.setVisibility(View.VISIBLE);
            holder.txt_tennhom.setText(item.getName());
            if (item.getImg_backgroup() > 0)
                switch (item.getImg_backgroup()) {
                    case 1:
                        Glide.with(context).load(R.drawable.group_0).into(holder.img_background_group);
                        break;
                    case 2:
                        Glide.with(context).load(R.drawable.group_1).into(holder.img_background_group);
                        break;
                    case 3:
                        Glide.with(context).load(R.drawable.group_2).into(holder.img_background_group);
                        break;
                    case 4:
                        Glide.with(context).load(R.drawable.group_4).into(holder.img_background_group);
                        break;
                    case 5:
                        Glide.with(context).load(R.drawable.group_0).into(holder.img_background_group);
                        break;
                    case 6:
                        Glide.with(context).load(R.drawable.group_3).into(holder.img_background_group);
                        break;
                }
            else
                Glide.with(context).load(R.drawable.group_3).into(holder.img_background_group);
            // Glide.with(context).load(R.drawable.spr_cam).into(holder.img_background_group);
        } else {
            String sdt = "";
            if (item.getClis() != null) {
                for (int i = 0; i < item.getClis().getCLI().size(); i++) {
                    if (i < (item.getClis().getCLI().size() - 1)) {
                        sdt = sdt + PhoneNumber.convertToVnPhoneNumber(item.getClis().getCLI().get(i).getCaller_id()) + ", ";
                    } else
                        sdt = sdt + PhoneNumber.convertToVnPhoneNumber(item.getClis().getCLI().get(i).getCaller_id());
                }
            }
            holder.txt_tennhom.setVisibility(View.GONE);
            holder.txt_name_group.setVisibility(View.VISIBLE);
            holder.txt_phone_group.setVisibility(View.VISIBLE);
            holder.txt_name_group.setText("Nhóm: " + item.getName());
            holder.txt_phone_group.setText("Số gọi đến: " + sdt);

            if (item.getImg_backgroup() > 0) {
                if (item.getImg_backgroup() == 1) {
                    Glide.with(context).load(R.drawable.group_0).into(holder.img_background_group);
                } else if (item.getImg_backgroup() == 2) {
                    Glide.with(context).load(R.drawable.group_1).into(holder.img_background_group);
                } else if (item.getImg_backgroup() == 3) {
                    Glide.with(context).load(R.drawable.group_2).into(holder.img_background_group);
                } else if (item.getImg_backgroup() == 4) {
                    Glide.with(context).load(R.drawable.group_4).into(holder.img_background_group);
                } else if (item.getImg_backgroup() == 5) {
                    Glide.with(context).load(R.drawable.group_0).into(holder.img_background_group);
                } else if (item.getImg_backgroup() == 6) {
                    Glide.with(context).load(R.drawable.group_3).into(holder.img_background_group);
                }
            }
               /* switch (item.getImg_backgroup()) {
                    case 1:
                        Glide.with(context).load(R.drawable.background_2).into(holder.img_background_group);
                        break;
                    case 2:
                        Glide.with(context).load(R.drawable.background_1).into(holder.img_background_group);
                        break;
                    case 3:
                        Glide.with(context).load(R.drawable.background_4).into(holder.img_background_group);
                        break;
                    case 4:
                        Glide.with(context).load(R.drawable.background_3).into(holder.img_background_group);
                        break;
                    case 5:
                        Glide.with(context).load(R.drawable.background_5).into(holder.img_background_group);
                        break;
                    case 6:
                        Glide.with(context).load(R.drawable.background_6).into(holder.img_background_group);
                        break;
                }*/
            else
                Glide.with(context).load(R.drawable.group_3).into(holder.img_background_group);
          /*  if (item.getImg_backgroup() != 0) {
                holder.relative_nhom.setBackground(context.getResources().getDrawable(item.getImg_backgroup()));
            } else {
                holder.relative_nhom.setBackground(context.getResources().getDrawable(R.drawable.spr_cam));
            }*/

            //  Glide.with(context).load(R.drawable.spr_cam).into(holder.img_background_group);
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.relative_nhom)
        CardView relative_nhom;
        @BindView(R.id.txt_tennhom)
        TextView txt_tennhom;
        @BindView(R.id.txt_name_group)
        TextView txt_name_group;
        @BindView(R.id.img_background_group)
        ImageView img_background_group;
        @BindView(R.id.txt_phone_group)
        TextView txt_phone_group;

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
