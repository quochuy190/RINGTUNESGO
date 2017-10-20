package com.neo.media.Fragment.Singer.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neo.media.Adapter.AdapterSinger;
import com.neo.media.Fragment.Singer.Presenter.Fragment_SingerDetail;
import com.neo.media.Fragment.Singer.Presenter.PresenterSinger;
import com.neo.media.Model.Singer;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by QQ on 7/7/2017.
 */

public class FragmentSinger extends BaseFragment implements FragmentSingerImpl,SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = FragmentSinger.class.getSimpleName();
    public static FragmentSinger fragmentSingerDetail;

    public static FragmentSinger getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentSinger.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentSinger();
            }
        }
        return fragmentSingerDetail;
    }
    PresenterSinger presenterSinger;
    AdapterSinger adapterSinger;
    @BindView(R.id.recycle_fragment)
    RecyclerView recycleSinger;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.txt_title_fragment)
    TextView txt_title;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    int page = 1;
    int index = 20;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    List<Singer> lisSinger;
    @BindView(R.id.prb_loading_more_all)
    public ProgressBar progressBar;
    SharedPreferences fr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        presenterSinger = new PresenterSinger(this);
        lisSinger = new ArrayList<>();
        presenterSinger.getAllSinger(""+page, ""+index);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);

      //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        initPulltoRefresh();
      //  MainActivity.relatic_tab.setVisibility(View.VISIBLE);
        initEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initEvent() {
        recycleSinger.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            presenterSinger.getAllSinger(""+page, ""+index);
                            CountDownTimer countDownTimer = new CountDownTimer(5 * 200, 200) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFinish() {
                                    progressBar.setVisibility(View.GONE);
                                }
                            };
                            countDownTimer.start();
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void init() {
        adapterSinger = new AdapterSinger(lisSinger, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
       // recycleSinger.setNestedScrollingEnabled(false);
        recycleSinger.setHasFixedSize(true);
        recycleSinger.setLayoutManager(mLayoutManager);
        recycleSinger.setItemAnimator(new DefaultItemAnimator());
        recycleSinger.setAdapter(adapterSinger);
        adapterSinger.notifyDataSetChanged();

        adapterSinger.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("name_singer", lisSinger.get(position).getSinger_name());
                editor.putString("hits_singer", lisSinger.get(position).getHits());
                editor.putString("url_imgSinger", lisSinger.get(position).getPhoto());
                editor.putString("idSinger", lisSinger.get(position).getId());
                editor.commit();
                FragmentUtil.addFragment(getActivity(), Fragment_SingerDetail.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    @Override
    public void showAllTopic(final List<Singer> albumList) {
        isLoading = false;
        progressBar.setVisibility(View.GONE);
        hideDialogLoading();
        if (albumList != null && albumList.size() > 0) {
            isLoading= false;
            lisSinger.addAll(albumList);
            Log.i(TAG, page+" lis mới "+albumList.size()+" tổng list "+lisSinger.size()+" index "+index);
            adapterSinger.notifyDataSetChanged();
        }

    }
    private void initPulltoRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                lisSinger = new ArrayList<Singer>();
                page = 1;
                init();
                isLoading = true;
                presenterSinger.getAllSinger(""+page, ""+index);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }

    @Override
    public void hideDialogLoading() {
        super.hideDialogLoading();
    }

    @Override
    public void showDialogLoading() {
        super.showDialogLoading();
    }
}
