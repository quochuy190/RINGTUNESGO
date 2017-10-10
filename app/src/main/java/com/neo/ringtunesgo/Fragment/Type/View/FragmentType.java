package com.neo.ringtunesgo.Fragment.Type.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neo.ringtunesgo.Adapter.AdapterType;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.ringtunesgo.Fragment.Type.Presenter.PresenterType;
import com.neo.ringtunesgo.Model.Type;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by QQ on 7/7/2017.
 */

public class FragmentType extends BaseFragment implements FragmentTypeImpl, SwipeRefreshLayout.OnRefreshListener {
    public static FragmentType fragmentSingerDetail;

    public static FragmentType getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentType.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentType();
            }
        }
        return fragmentSingerDetail;
    }

    PresenterType presenterType;
    AdapterType adapterSinger;
    @BindView(R.id.recycle_fragment)
    RecyclerView recycleType;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.txt_title_fragment)
    TextView txt_title;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    int page = 1;
    int index = 20;
    boolean isLoading = true;
    List<Type> lisTypes;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    @BindView(R.id.prb_loading_more_all)
    public ProgressBar progressBar;
    SharedPreferences fr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        presenterType = new PresenterType(this);
        lisTypes = new ArrayList<>();
        presenterType.getallElement("" + page, "" + index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        init();
        initPulltoRefresh();
        //    ab.setTitle("Thể Loại");
        txt_title.setVisibility(View.GONE);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initEvent() {
      /*  recycleType.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy>0){
                    GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView
                            .getLayoutManager();
                    visibleItemCount = layoutmanager.getChildCount();
                    totalItemCount = layoutmanager.getItemCount();
                    pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();
                    if((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                        if (!isLoading){
                            isLoading = true;
                            page++;
                            presenterType.getallElement(""+page, ""+index);
                            CountDownTimer countDownTimer = new CountDownTimer(5 * 200, 200) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                  //  progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFinish() {

                                }
                            };
                            countDownTimer.start();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
    }

    private void init() {
        adapterSinger = new AdapterType(lisTypes, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        // recycleType.setNestedScrollingEnabled(false);
        recycleType.setHasFixedSize(true);
        recycleType.setLayoutManager(mLayoutManager);
        recycleType.setItemAnimator(new DefaultItemAnimator());
        recycleType.setAdapter(adapterSinger);
        adapterSinger.notifyDataSetChanged();
        adapterSinger.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.TYPE);
                editor.putString("title", lisTypes.get(position).getName());
                editor.putString("id", lisTypes.get(position).getId());
                editor.putString("url_image_title", lisTypes.get(position).getThumbnal_image());
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });

    }

    @Override
    public void showAllTopic(final List<Type> albumList) {
        lisTypes.clear();
        hideDialogLoading();
        if (albumList != null && albumList.size() > 0) {
            isLoading = false;
            lisTypes.addAll(albumList);
            adapterSinger.notifyDataSetChanged();
        }

    }

    private void initPulltoRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                init();
                isLoading = true;
                presenterType.getallElement("" + page, "" + index);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
