package com.neo.ringtunesgo.Fragment.Album.View;

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
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neo.ringtunesgo.Adapter.AdapterAlbum;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Fragment.Album.Presenter.PresenterAlbum;
import com.neo.ringtunesgo.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.ringtunesgo.MainNavigationActivity;
import com.neo.ringtunesgo.Model.Album;
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

public class FragmentAlbum extends BaseFragment implements FragmentAlbumImpl, SwipeRefreshLayout.OnRefreshListener{
    public static String TAG = FragmentAlbum.class.getSimpleName();
    public static FragmentAlbum fragmentSingerDetail;

    public static FragmentAlbum getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentAlbum.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentAlbum();
            }
        }
        return fragmentSingerDetail;
    }
    PresenterAlbum presenterHome;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycle_fragment)
    RecyclerView recycleAlbum;
    @BindView(R.id.txt_title_fragment)
    TextView txt_title;
    AdapterAlbum adapterAlbum ;
    RecyclerView.LayoutManager mLayoutManager;
    List<Album> lisAlbum;
    int page = 1;
    int index = 20;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean isLoading = true;
    @BindView(R.id.prb_loading_more_all)
    public ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
        initPulltoRefresh();
        txt_title.setVisibility(View.GONE);

        initEvent();
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
        showDialogLoading();
        presenterHome.getallElement(""+page, ""+index);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void initEvent() {
        recycleAlbum.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            presenterHome.getallElement(""+page, ""+index);
                            CountDownTimer countDownTimer = new CountDownTimer(5 * 200, 200) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    progressBar.setVisibility(View.VISIBLE);
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
        });
    }

    private void init() {
        presenterHome = new PresenterAlbum(this);
        lisAlbum = new ArrayList<>();
        adapterAlbum = new AdapterAlbum(lisAlbum,getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        //  recycleAlbum.setNestedScrollingEnabled(false);
        recycleAlbum.setHasFixedSize(true);
        recycleAlbum.setLayoutManager(mLayoutManager);
        recycleAlbum.setItemAnimator(new DefaultItemAnimator());
        recycleAlbum.setAdapter(adapterAlbum);
    }


    @Override
    public void hideDialogLoading() {
        super.hideDialogLoading();
    }

    private void initPulltoRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showAllAlbum(final List<Album> listAlbum) {
        progressBar.setVisibility(View.GONE);
        if(listAlbum!=null&&listAlbum.size()>0){
            hideDialogLoading();
            isLoading = false;
            lisAlbum.addAll(listAlbum);
            Log.i(TAG, page+"lis mới "+listAlbum.size()+"tổng list"+lisAlbum.size());
            adapterAlbum.notifyDataSetChanged();

            adapterAlbum.setOnIListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    SharedPreferences fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = fr.edit();
                    editor.putString("url_image_title", lisAlbum.get(position).getPhoto());
                    editor.commit();
                    MainNavigationActivity.title_lisSongs = Config.ALBUM;
                    MainNavigationActivity.id_lisSongs = listAlbum.get(position).getId();

                    //  FragmentUtil.addFragmentData(getActivity(), FragmentSongs.getInstance(), true, bundle);
                    FragmentUtil.replaceFragment(getActivity().getSupportFragmentManager(), R.id.fame_main,
                            FragmentSongs.getInstance(), FragmentAlbum.class.getSimpleName(), FragmentSongs.TAG);
                }

                @Override
                public void OnLongItemClickListener(int position) {

                }
            });
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                page = 1;
                init();
                isLoading = true;
                presenterHome.getallElement(""+page, ""+index);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }
}
