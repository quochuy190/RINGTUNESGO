package com.neo.media.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neo.media.Model.Comment;
import com.neo.media.R;
import com.neo.media.untils.setOnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.TopicViewHoder>{
    private List<Comment> listSinger;
    private Context context;
    private setOnItemClickListener OnIListener;

    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterComment(List<Comment> listSinger, Context context) {
        this.listSinger = listSinger;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment,parent,false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        final Comment topic = listSinger.get(position);
        //String urlImage = IMAGE_URL + topic.getPhoto();
        //Glide.with(context).load(urlImage).into(holder.imgTocpicPhoto);
        holder.txtTocpicName.setText(topic.getNAME_());
        holder.txtTime_Comment.setText(topic.getCREATE_DATE());
        holder.txt_comment_content.setText(topic.getCONTENT());

     /*   holder.imgSingerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Detail_Ringtunes.class);
                intent.putExtra("id", topic.getId());
                intent.putExtra("id_type", Config.SINGER);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listSinger.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.item_img_comment)
        CircleImageView imgTocpicPhoto;
        @BindView(R.id.item_name_comment)
        TextView txtTocpicName;
        @BindView(R.id.item_time_comment)
        TextView txtTime_Comment;
        @BindView(R.id.item_conten_comment)
        TextView txt_comment_content;
  /*      @BindView(R.id.item_hits_home)
        TextView txtHome_Hits;*/

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           // itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         //   OnIListener.OnItemClickListener(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
           // OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }
}
