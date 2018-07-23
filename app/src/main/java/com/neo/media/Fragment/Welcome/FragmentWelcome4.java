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

public class FragmentWelcome4 extends BaseFragment {
    public static FragmentWelcome4 fragmentSingerDetail;

    public static FragmentWelcome4 getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentWelcome4.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentWelcome4();
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
        Glide.with(getContext()).load(R.drawable.welcome3).into(img_welcome);
        txt_title_welcome.setText("LÀM CHỦ NHẠC CHỜ CỦA MÌNH");
        txt_content_welcome.setText("Nhạc chờ là \"Status\" của bạn trên sóng điện thoại\n" +
                "Hãy cập nhật nhạc chờ mỗi ngày để những người thân yêu lắng nghe được tâm trạng của bạn mỗi khi chờ bạn nhấc máy");


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
