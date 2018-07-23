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

public class FragmentWelcome2 extends BaseFragment  {
    public static FragmentWelcome2 fragmentSingerDetail;

    public static FragmentWelcome2 getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentWelcome2.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentWelcome2();
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
        Glide.with(getContext()).load(R.drawable.welcome1).into(img_welcome);
        txt_title_welcome.setText("TIẾP CẬN KHO NHẠC CHỜ SỐNG ĐỘNG");
        txt_content_welcome.setText("Ringtunes đầy ắp những ca khúc nhạc chờ đình đám để bạn lựa chọn\n" +
                "Truy cập Ringtunes mỗi ngày để cập nhật nững bản nhạc chờ mới nhất, Hot nhất mang hơi thở của cuộc sống");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
