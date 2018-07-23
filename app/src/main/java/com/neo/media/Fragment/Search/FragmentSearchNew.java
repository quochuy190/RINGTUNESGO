package com.neo.media.Fragment.Search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neo.media.Adapter.AdapterHistoryKeyWord;
import com.neo.media.Adapter.AdapterRingtunes;
import com.neo.media.Adapter.AdapterTopic;
import com.neo.media.Config.Config;
import com.neo.media.Fragment.BuySongs.View.FragmentDetailBuySongs;
import com.neo.media.Fragment.DetailSongs.View.FragmentSongs;
import com.neo.media.Fragment.Home.Topic.View.FragmentTocpic;
import com.neo.media.Model.KeyWord;
import com.neo.media.Model.Ringtunes;
import com.neo.media.Model.SearchData;
import com.neo.media.Model.Topic;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.RealmController.RealmController;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.KeyboardUtil;
import com.neo.media.untils.setOnItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by QQ on 11/30/2017.
 */

public class FragmentSearchNew extends BaseFragment {
    public static FragmentSearchNew fragment;

    public static FragmentSearchNew getInstance() {
        if (fragment == null) {
            synchronized (FragmentSearchNew.class) {
                if (fragment == null) {
                    fragment = new FragmentSearchNew();
                }
            }
        }
        return fragment;
    }

    @BindView(R.id.edt_search)
    EditText edt_search;
    PresenterSearch presenterSearch;
    String user_id;
    SharedPreferences pre;
    @BindView(R.id.parent_top_history)
    LinearLayout parent_top_history;
    SharedPreferences fr;
    ArrayList<KeyWord> listKeyWord;
    @BindView(R.id.img_delete_text)
    ImageView img_delete_text;
    @BindView(R.id.linner_goiy_search)
    LinearLayout linner_goiy_search;
    @BindView(R.id.linner_list_baihat_search)
    LinearLayout linner_list_baihat_search;
    @BindView(R.id.txt_search_baihat)
    TextView txt_search_baihat;
    @BindView(R.id.txt_search_casi)
    TextView txt_search_casi;
    @BindView(R.id.txt_search_mienphi)
    TextView txt_search_mienphi;
    @BindView(R.id.linner_more_chudehot)
    RelativeLayout linner_more_chudehot;
    Realm realm;
    ArrayList<String> array;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("search", "onCreate");
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        user_id = Prefs.with(getActivity()).getString("user_id", "");
        is_search_mienphi = false;
        is_search_casi = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (is_search_baihat) {
            txt_search_baihat.setBackgroundColor(getResources().getColor(R.color.background));
        } else {
            txt_search_baihat.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if (is_search_casi) {
            txt_search_casi.setBackgroundColor(getResources().getColor(R.color.background));
        } else {
            txt_search_casi.setBackgroundColor(getResources().getColor(R.color.white));
        }
        if (is_search_mienphi) {
            txt_search_mienphi.setBackgroundColor(getResources().getColor(R.color.background));
        } else {
            txt_search_mienphi.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("search", "onPause");
        KeyboardUtil.hideSoftKeyboard(getActivity());
        img_delete_text.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_new, container, false);
        ButterKnife.bind(this, view);
        fr = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //  KeyboardUtil.requestKeyboard(getActivity(), R.id.edt_search);
        realm = RealmController.with(this).getRealm();
        edt_search.requestFocus();
        /*InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
        listKeyWord = new ArrayList<>();
        presenterSearch = new PresenterSearch(this);
        // presenterSearch.search_keyword_top(user_id);
        listKeyWord.addAll(MyApplication.listKeyword);
        populateLinks(parent_top_history, listKeyWord);
        init();
        initTopic();
        initEvent();
        history_key_word();
        return view;
    }

    public void add_content_search(String content_search, Date date) {
        boolean isUpdate = false;
        if (array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).equals(content_search)) {
                    isUpdate = true;
                }
            }
        }
        if (!isUpdate) {
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

    private boolean is_search_baihat = true;
    private boolean is_search_casi = false;
    private boolean is_search_mienphi = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String key = "";
    int page = 1;
    int index = 30;
    String stype = "1";
    Date date;

    private void initEvent() {
        // xoá text search
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                img_delete_text.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // button search
        edt_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (edt_search.getText().toString().length() > 0) {

                                // init();
                                KeyboardUtil.hideSoftKeyboard(getActivity());
                                // InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                //inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                key = edt_search.getText().toString();
                                search_key(key);

                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        // loadmore
        recycle_baihat.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            showDialogLoading();
                            //  key = ed_key_search_fragment.getText().toString();
                            presenterSearch.getSearch(key, stype, "" + page, "" + index, user_id);

                        }
                    }
                }
            }
        });
        linner_more_chudehot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.addFragment(getActivity(), FragmentTocpic.getInstance(), true);
            }
        });
        img_delete_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setText("");
                img_delete_text.setVisibility(View.GONE);
                linner_goiy_search.setVisibility(View.VISIBLE);
                linner_list_baihat_search.setVisibility(View.GONE);
            }
        });
        txt_search_baihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_search_baihat) {
                    txt_search_baihat.setBackgroundColor(getResources().getColor(R.color.background));
                    is_search_baihat = !is_search_baihat;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                } else {
                    txt_search_baihat.setBackgroundColor(getResources().getColor(R.color.white));
                    is_search_baihat = !is_search_baihat;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                }
            }
        });
        txt_search_casi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_search_casi) {
                    txt_search_casi.setBackgroundColor(getResources().getColor(R.color.background));
                    is_search_casi = !is_search_casi;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                } else {
                    txt_search_casi.setBackgroundColor(getResources().getColor(R.color.white));
                    is_search_casi = !is_search_casi;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                }

            }
        });
        txt_search_mienphi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!is_search_mienphi) {
                    txt_search_mienphi.setBackgroundColor(getResources().getColor(R.color.background));
                    is_search_mienphi = !is_search_mienphi;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                } else {
                    txt_search_mienphi.setBackgroundColor(getResources().getColor(R.color.white));
                    is_search_mienphi = !is_search_mienphi;
                    if (edt_search.getText().length() > 0) {
                        search_key(edt_search.getText().toString());
                    }
                }

            }
        });
    }

    private void search_key(String key) {
        edt_search.setText(key);
        KeyboardUtil.hideSoftKeyboard(getActivity());
        showDialogLoading();
        listRingtunes.clear();
        adapterRingtunes.notifyDataSetChanged();
        img_delete_text.setVisibility(View.VISIBLE);
        linner_goiy_search.setVisibility(View.GONE);
        linner_list_baihat_search.setVisibility(View.VISIBLE);
        stype = get_type();
        page = 1;
        date = Calendar.getInstance().getTime();
        add_content_search(key, date);
        history_key_word();
        presenterSearch.getSearch(key, stype, "" + page, "" + index, user_id);
    }

    private String get_type() {
        if (is_search_baihat && is_search_casi && is_search_mienphi) {
            return "7";
        } else if (is_search_baihat && is_search_casi) {
            return "5";
        } else if (is_search_baihat && is_search_mienphi) {
            return "4";
        } else if (is_search_mienphi && is_search_casi) {
            return "6";
        } else if (is_search_casi) {
            return "2";
        } else if (is_search_mienphi) {
            return "3";
        } else if (is_search_casi) {
            return "1";
        }
        return "1";
    }

    public void show_search_keyword(ArrayList<KeyWord> list) {
        listKeyWord.clear();
        list.add(new KeyWord("Tuấn hưng", "10"));
        list.add(new KeyWord("Đàm Vĩnh Hưng", "10"));
        list.add(new KeyWord("Mỹ Tâm", "10"));
        list.add(new KeyWord("Thuỳ chi", "10"));
        listKeyWord.addAll(list);

    }

    private void populateLinks(LinearLayout ll, ArrayList<KeyWord> collection) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth() - 10;
        // DebugLog.i("maxWidth---->" + maxWidth);
        if (collection.size() > 0) {
            LinearLayout llAlso = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 20, 10, 0);
            llAlso.setLayoutParams(params);
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            int widthSoFar = 0;
            for (KeyWord keyWord : collection) {
                final TextView txtSamItem = new TextView(getContext(), null,
                        android.R.attr.textColorLink);
                txtSamItem.setText(keyWord.KEYWORD.toLowerCase());
                txtSamItem.setTag(keyWord.getKEYWORD());
                txtSamItem.setTextColor(getResources().getColor(R.color.purple));
                txtSamItem.setPadding(30, 15, 30, 15);
                txtSamItem.setBackground(getResources().getDrawable(R.drawable.bg_search_history));
                txtSamItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search_key(txtSamItem.getText().toString());
                       /* containerHistorySearch.setVisibility(View.GONE);
                        // searchRing.setQuery(txtSamItem.getText().toString(), false);
                        performResultSearch(txtSamItem.getText().toString());*/
                    }
                });
                txtSamItem.measure(0, 0);
                widthSoFar = widthSoFar + txtSamItem.getMeasuredWidth();
                // DebugLog.i("widthSoFar------------" + widthSoFar);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(10, 0, 10, 0);
                txtSamItem.setLayoutParams(params1);
                if (widthSoFar + 100 >= maxWidth) {
                    ll.addView(llAlso);
                    llAlso = new LinearLayout(getContext());
                    llAlso.setLayoutParams(params);
                    llAlso.setOrientation(LinearLayout.HORIZONTAL);
                    llAlso.addView(txtSamItem);
                    widthSoFar = txtSamItem.getMeasuredWidth();
                } else {
                    llAlso.addView(txtSamItem);
                }
            }
            ll.addView(llAlso);
        }
    }

    @BindView(R.id.recycle_search_chudehot)
    RecyclerView recycle_search_chudehot;
    AdapterTopic adapterTopic;
    RecyclerView.LayoutManager mLayoutManager;
    List<Topic> listTopic;

    public void initTopic() {
        listTopic = new ArrayList<>();
        listTopic.addAll(MyApplication.lisHotTopic);
        adapterTopic = new AdapterTopic(listTopic, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        recycle_search_chudehot.setNestedScrollingEnabled(false);
        recycle_search_chudehot.setHasFixedSize(true);
        recycle_search_chudehot.setLayoutManager(mLayoutManager);
        recycle_search_chudehot.setItemAnimator(new DefaultItemAnimator());
        recycle_search_chudehot.setAdapter(adapterTopic);
        adapterTopic.notifyDataSetChanged();

        adapterTopic.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences.Editor editor = fr.edit();
                editor.putString("option", Config.TOPIC);
                editor.putString("title", listTopic.get(position).getPackage_name());
                editor.putString("id", listTopic.get(position).getId());
                editor.putString("url_image_title", listTopic.get(position).getPhoto());
                editor.commit();
                if (!FragmentSongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentSongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    AdapterRingtunes adapterRingtunes;
    @BindView(R.id.recycle_list_baihat_search)
    RecyclerView recycle_baihat;
    List<Ringtunes> listRingtunes;

    private void init() {
        listRingtunes = new ArrayList<>();
        adapterRingtunes = new AdapterRingtunes(listRingtunes, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_baihat.setHasFixedSize(false);
        recycle_baihat.setLayoutManager(mLayoutManager);
        recycle_baihat.setItemAnimator(new DefaultItemAnimator());
        recycle_baihat.setAdapter(adapterRingtunes);
        adapterRingtunes.notifyDataSetChanged();

        adapterRingtunes.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                SharedPreferences pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = pre.edit();
                MyApplication.player_ring = Ringtunes.getInstance();
                MyApplication.player_ring = listRingtunes.get(position);
                editor.putBoolean("isHome", false);
                editor.putString("idSinger", listRingtunes.get(position).getSinger_id());
                editor.commit();
                if (!FragmentDetailBuySongs.getInstance().isAdded())
                    FragmentUtil.addFragment(getActivity(), FragmentDetailBuySongs.getInstance(), true);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }

    boolean isLoading = true;

    public void showListSearch(List<Ringtunes> ringtunes) {
        hideDialogLoading();
        //  progressBar.setVisibility(View.GONE);
        if (ringtunes.size() > 0) {
            isLoading = false;
        }
        listRingtunes.addAll(ringtunes);
        if (listRingtunes != null && listRingtunes.size() > 0) {
            //  txt_notification_search.setVisibility(View.GONE);
            //linearLayout.setBackgroundResource(R.color.background_item);
            linner_goiy_search.setVisibility(View.GONE);
            linner_list_baihat_search.setVisibility(View.VISIBLE);
            adapterRingtunes.notifyDataSetChanged();
        } else {

        }
    }

    // danh sách từ khoá đã tìm kiếm
    AdapterHistoryKeyWord adapter;
    @BindView(R.id.recycle_search_lichsu)
    RecyclerView recycle_search;

    public void history_key_word() {
        RealmResults<SearchData> result = realm.where(SearchData.class).findAll();
        result.sort("time_search", false); // for sorting descending
        array = new ArrayList<>();
        int size = result.size();
        Log.i(TAG, "" + size);
        for (int i = 0; i < size; i++) {
            SearchData search = result.get(i);
            Log.i(TAG, search.getContent_search());
            Log.i(TAG, search.getId() + "");
            array.add(search.getContent_search());
        }
        adapter = new AdapterHistoryKeyWord(array, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 1);
        recycle_search.setNestedScrollingEnabled(false);
        recycle_search.setHasFixedSize(true);
        recycle_search.setLayoutManager(mLayoutManager);
        recycle_search.setItemAnimator(new DefaultItemAnimator());
        recycle_search.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setSetOnItemClickListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                search_key(array.get(position));
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });

    }
}
