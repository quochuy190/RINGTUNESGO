package com.neo.media.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.Fragment.CaNhan.Collection.PresenterConllection;
import com.neo.media.Fragment.CaNhan.Groups.GroupMember.FragmentGroupMember;
import com.neo.media.Model.PhoneContactModel;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by QQ on 7/7/2017.
 */

public class Adapter_Group_Member extends RecyclerView.Adapter<Adapter_Group_Member.TopicViewHoder> {
    private List<PhoneContactModel> lisContact;
    private Context context;
    private setOnItemClickListener setOnItemClickListener;
    private PresenterConllection presenterConllection;


    public com.neo.media.untils.setOnItemClickListener getSetOnItemClickListener() {
        return setOnItemClickListener;
    }

    public void setSetOnItemClickListener(com.neo.media.untils.setOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public Adapter_Group_Member(List<PhoneContactModel> lisContact, Context context) {
        this.lisContact = lisContact;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_member, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, final int position) {
        final PhoneContactModel contact = lisContact.get(position);
        //Glide.with(context).load(urlImage).into(holder.imgRingtunes);
        holder.txtRingtunescName.setText(contact.getName());
        holder.txtRingtunesSinger.setText(contact.getPhoneNumber());
        if (contact.isShowDelete()) {
            holder.img_delete_cli.setVisibility(View.VISIBLE);
        } else holder.img_delete_cli.setVisibility(View.GONE);
        holder.img_delete_cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGroupMember.delete_cli_to_group(context,contact.getPhoneNumber());
            }
        });
    /*    holder.img_recyclebin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentGroupMember.delete_cli_to_group(sesionID,msisdn, lisContact.get(position).toString() );
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return lisContact.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_g_member_avata)
        CircleImageView imgRingtunes;
        @BindView(R.id.item_g_member_name)
        TextView txtRingtunescName;
        @BindView(R.id.item_g_member_phone)
        TextView txtRingtunesSinger;
        @BindView(R.id.img_delete_cli)
        ImageView img_delete_cli;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
