package com.neo.ringtunesgo.Fragment.Topic.View;

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

import com.neo.ringtunesgo.Adapter.AdapterTopic;
import com.neo.ringtunesgo.Config.Config;
import com.neo.ringtunesgo.Fragment.Album.View.FragmentAlbum;
import com.neo.ringtunesgo.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.ringtunesgo.Fragment.Topic.Presenter.PresenterTopic;
import com.neo.ringtunesgo.Model.Topic;
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

public class FragmentTocpic  extends BaseFragment implements FragmentTopicImpl, SwipeRefreshLayout.OnRefreshListener{
    String TAG = FragmentAlbum.class.getSimpleName();
    public static FragmentTocpic fragmentSingerDetail;

    public static FragmentTocpic getInstance() {
        if (fragmentSingerDetail == null) {
            synchronized (FragmentTocpic.class) {
                if (fragmentSingerDetail == null)
                    fragmentSingerDetail = new FragmentTocpic();
            }
        }
        return fragmentSingerDetail;
    }
    PresenterTopic presenterTopic;
    @BindView(R.id.recycle_fragment)
    RecyclerView recycleTopic;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterTopic adapterTopic ;
    @BindView(R.id.pull_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    String page;
    String index;
    List<Topic> lisTopic;
    SharedPreferences fr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        page = "1";
        index = "20";
        lisTopic = new ArrayList<>();
        presenterTopic = new PresenterTopic(this);
        presenterTopic.getallElement(page, index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        initTopic();
        initPulltoRefresh();

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
    public void initTopic(){
        adapterTopic = new AdapterTopic(lisTopic,getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        // recycleTopic.setNestedScrollingEnabled(false);
        recycleTopic.setHasFixedSize(true);
        recycleTopic.setLayoutManager(mLayoutManager);
        recycleTopic.setItemAnimator(new DefaultItemAnimator());
        recycleTopic.setAdapter(adapterTopic);
        adapterTopic.notifyDataSetChanged();

        adapterTopic.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.TOPIC);
                editor.putString("title", lisTopic.get(position).getPackage_name());
                editor.putString("id", lisTopic.get(position).getId());
                editor.putString("url_image_title",lisTopic.get(position).getPhoto());
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
    public void showAllTopic(final List<Topic> listTopic) {
        if(listTopic!=null&&listTopic.size()>0){
            lisTopic.clear();
            lisTopic.addAll(listTopic);
            adapterTopic.notifyDataSetChanged();
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
                presenterTopic.getallElement(page, index);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
