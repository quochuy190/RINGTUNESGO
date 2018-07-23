package com.neo.media.Fragment.CaNhan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by QQ on 8/16/2017.
 */

public class FragmentDieukhoan extends BaseFragment {

    public static FragmentDieukhoan fragmentStopPause;

    public static FragmentDieukhoan getInstance() {
        if (fragmentStopPause == null) {
            synchronized (FragmentDieukhoan.class) {
                if (fragmentStopPause == null)
                    fragmentStopPause = new FragmentDieukhoan();
            }
        }
        return fragmentStopPause;
    }


    @BindView(R.id.txt_dieukhoan)
    TextView txt_dieukhoan;
    @BindView(R.id.img_back_dieukoan)
    ImageView img_back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dieukhoan, container, false);
        ButterKnife.bind(this, view);
        String s = getResources().getString(R.string.dieukhoan);
      //  txt_dieukhoan.setText(Html.fromHtml(s));
       // txt_dieukhoan.setText(Utilities.getSpannedText(s));
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.popBackStack(getActivity());
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
