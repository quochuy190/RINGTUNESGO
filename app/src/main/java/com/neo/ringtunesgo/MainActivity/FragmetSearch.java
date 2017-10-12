package com.neo.ringtunesgo.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.ringtunesgo.Adapter.AdapterRingtunes;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Model.SearchData;
import com.neo.ringtunesgo.MyApplication;
import com.neo.ringtunesgo.Player.PlaybackService;
import com.neo.ringtunesgo.R;
import com.neo.ringtunesgo.RealmController.RealmController;
import com.neo.ringtunesgo.untils.BaseFragment;
import com.neo.ringtunesgo.untils.FragmentUtil;
import com.neo.ringtunesgo.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.ringtunesgo.MainNavigationActivity.appbar;


/**
 * Created by QQ on 7/20/2017.
 */

public class FragmetSearch extends BaseFragment implements MainActivityImpl.View {
    public static String TAG = FragmetSearch.class.getSimpleName();
    public static FragmetSearch fragment;
    @BindView(R.id.list_songs_search)
    RecyclerView recyclerSearch;
 /*   @BindView(R.id.ed_key_search_fragment)
    EditText ed_key_search_fragment;*/
    @BindView(R.id.txt_notification_search)
    TextView txt_notification_search;
    @BindView(R.id.txt_shutdown_text)
    TextView txt_shutdown;
    @BindView(R.id.Linner_Search)
    RelativeLayout Linner_Search;
    Date date;
    List<Ringtunes> listRingtunes;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterRingtunes adapterRingtunes;
    PresenterMainActivity presenterMainActivity;
    String key;
    @BindView(R.id.auto_edt_search)
    AutoCompleteTextView auto_edt_search;
    int page = 1, index = 20;
    boolean isLoading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    Realm realm;
    ArrayList<String> array;
    @BindView(R.id.prb_loading_more)
    public ProgressBar progressBar;

    public static FragmetSearch getInstance() {
        if (fragment == null) {
            synchronized (FragmetSearch.class) {
                if (fragment == null) {
                    fragment = new FragmetSearch();
                }
            }
        }
        return fragment;
    }

    RelativeLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRingtunes = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        realm = RealmController.with(this).getRealm();
        linearLayout = (RelativeLayout) view.findViewById(R.id.Linner_Search);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(auto_edt_search, InputMethodManager.SHOW_IMPLICIT);
        presenterMainActivity = new PresenterMainActivity(this);
        init();
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
        appbar.setVisibility(View.GONE);
      /*  RealmResults<SearchData> sorted = realm.where(SearchData.class).findAllSorted("time_search", Sort.DESCENDING);
       */
       auto_complete();
    }
    public void auto_complete(){
        RealmResults<SearchData> result = realm.where(SearchData.class).findAll();
        result.sort("time_search", false); // for sorting descending
        array= new ArrayList<>();
        int size = result.size();
        Log.i(TAG, "" + size);
        for (int i = 0; i < size; i++) {
            SearchData search = result.get(i);
            Log.i(TAG, search.getContent_search());
            Log.i(TAG, search.getId() + "");
            array.add(search.getContent_search());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, array);
        auto_edt_search.setAdapter(adapter);
        auto_edt_search.setThreshold(1);

        auto_edt_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listRingtunes = new ArrayList<Ringtunes>();
                init();
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                txt_notification_search.setVisibility(View.GONE);
                Linner_Search.setBackgroundResource(R.drawable.backgroud_search);
                key = auto_edt_search.getText().toString();
                page = 1;
                showDialogLoading();
                presenterMainActivity.getSearch(key, "" + page, "" + index);
            }
        });

    }


    private void init() {
        adapterRingtunes = new AdapterRingtunes(listRingtunes, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(mLayoutManager);
        recyclerSearch.setItemAnimator(new DefaultItemAnimator());
        recyclerSearch.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences pre = getActivity().getSharedPreferences
                        ("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                editor.putString("idSinger", listRingtunes.get(position).getSinger_id());
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putSerializable("objRing", listRingtunes.get(position));
                FragmentUtil.addFragmentData(getActivity(), FragmentDetailBuySongs.getInstance(), true, bundle);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    private void initEvent() {
        auto_edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        auto_edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txt_shutdown.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        auto_edt_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (auto_edt_search.getText().toString().length() > 0) {
                                listRingtunes = new ArrayList<Ringtunes>();
                                init();
                                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                txt_notification_search.setVisibility(View.GONE);
                                Linner_Search.setBackgroundResource(R.drawable.backgroud_search);
                                key = auto_edt_search.getText().toString();
                                Log.i(TAG, key + " " + page);
                                page = 1;
                                showDialogLoading();
                                date = Calendar.getInstance().getTime();
                                add_content_search(key, date);
                                auto_complete();
                                presenterMainActivity.getSearch(key, "" + page, "" + index);
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        recyclerSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    GridLayoutManager layoutmanager = (GridLayoutManager) recyclerView
                            .getLayoutManager();
                    visibleItemCount = layoutmanager.getChildCount();
                    totalItemCount = layoutmanager.getItemCount();
                    pastVisiblesItems = layoutmanager.findFirstVisibleItemPosition();
                    //Log.i(TAG, visibleItemCount + " " + totalItemCount + " " + presenter_detail_ringtunes);
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        if (!isLoading) {
                            isLoading = true;
                            page++;
                            //  key = ed_key_search_fragment.getText().toString();
                            presenterMainActivity.getSearch(key, "" + page, "" + index);
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
            }
        });

        txt_shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_edt_search.setText("");
                txt_shutdown.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void showListSearch(List<Ringtunes> ringtunes) {
        hideDialogLoading();
        progressBar.setVisibility(View.GONE);
        if (ringtunes.size()>0){
            isLoading = false;
        }
        listRingtunes.addAll(ringtunes);
        if (listRingtunes != null && listRingtunes.size() > 0) {
            txt_notification_search.setVisibility(View.GONE);
            linearLayout.setBackgroundResource(R.color.background_item);
            auto_edt_search.setText("");
            txt_shutdown.setVisibility(View.GONE);
            adapterRingtunes.notifyDataSetChanged();

            adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
                @Override
                public void OnItemClickListener(int position) {
                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    SharedPreferences pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pre.edit();
                    MyApplication.player_ring = Ringtunes.getInstance();
                    MyApplication.player_ring = listRingtunes.get(position);
                    editor.putBoolean("isHome", false);
                    editor.putString("idSinger", listRingtunes.get(position).getSinger_id());
                    editor.commit();
                    FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
                }

                @Override
                public void OnLongItemClickListener(int position) {

                }
            });
        } else {
            txt_notification_search.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showInfo_User(subscriber subscriber) {

    }

    @Override
    public void show_init_service(String sUserNameId) {

    }

    @Override
    public void onPlaybackServiceBound(PlaybackService service) {

    }

    @Override
    public void onPlaybackServiceUnbound() {

    }

    @Override
    public void onPause() {
        super.onPause();
        appbar.setVisibility(View.VISIBLE);
        txt_notification_search.setVisibility(View.GONE);
    }

    public void add_content_search(String content_search, Date date) {
        boolean isUpdate =false;
        if (array.size()>0){
            for (int i = 0;i<array.size();i++){
                if (array.get(i).equals(content_search)){
                    isUpdate =true;
                }
            }
        }
        if (!isUpdate){
            realm.beginTransaction();
            SearchData objSearch = realm.createObject(SearchData.class);
            // increatement index
            int nextID = (int) (realm.where(SearchData.class).maximumInt("id") + 1);
            // insert new value
            objSearch.setId(nextID);
            objSearch.setContent_search(content_search);
            objSearch.setTime_search(date);
            realm.commitTransaction();
        }
    }

}
