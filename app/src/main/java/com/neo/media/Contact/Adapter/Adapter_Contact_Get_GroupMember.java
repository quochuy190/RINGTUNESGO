package com.neo.media.Contact.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.neo.media.Model.PhoneContactModel;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class Adapter_Contact_Get_GroupMember extends RecyclerView.Adapter<Adapter_Contact_Get_GroupMember.TopicViewHoder>{
    private List<PhoneContactModel> listService;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public Adapter_Contact_Get_GroupMember(List<PhoneContactModel> listTopic, Context context) {
        this.listService = listTopic;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact_picker,parent,false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        PhoneContactModel objService = listService.get(position);
        holder.item_contact_name.setText(objService.getName());
        holder.item_contact_phone.setText(objService.getPhoneNumber());
        holder.cb_contact.setChecked(objService.isChecked());
    }

    @Override
    public int getItemCount() {
        return listService.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    /*    @BindView(R.id.img_item_listservice)
        ImageView imgTocpicPhoto;*/
        @BindView(R.id.item_contact_name)
    TextView item_contact_name;
        @BindView(R.id.item_contact_phone)
        TextView item_contact_phone;
        @BindView(R.id.cb_contact)
        CheckBox cb_contact;



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


    public void updateList(List<PhoneContactModel> list){
        listService = list;
        notifyDataSetChanged();
    }
}
