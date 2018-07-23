package com.neo.media.Fragment.Welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by QQ on 7/7/2017.
 */

public class FragmentWelcome3 extends BaseFragment  {
    public static FragmentWelcome3 fragmentSingerDetail;

    public static FragmentWelcome3 getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentWelcome3.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentWelcome3();
            }
        }
        return fragmentSingerDetail;
    }
    @BindView(R.id.img_welcome)
    ImageView img_welcome;
    @BindView(R.id.txt_title_welcome)
    TextView txt_title_welcome;
    @BindView(R.id.txt_content_welcome)
    TextView txt_content_welcome;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_welcome, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(R.drawable.welcome2).into(img_welcome);

        txt_title_welcome.setText("TÌM KIẾM NHANH");
        txt_content_welcome.setText("Công cụ tìm kiếm thông minh giúp bạn nhanh chóng có được bài hát yêu thích dành tặng cho những người thân yêu\n" +
                "\n" +
                "Cám ơn bạn đã sử dụng Ringtunes");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
