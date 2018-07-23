package com.neo.media.Fragment.BuySongs.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.CRBTModel.Item;
import com.neo.media.Config.Constant;
import com.neo.media.Contact.ViewActivity.ActivityListContact;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongs;
import com.neo.media.Fragment.BuySongs.Presenter.PresenterSongsImpl;
import com.neo.media.Model.Comment;
import com.neo.media.Model.Ringtunes;
import com.neo.media.MyApplication;
import com.neo.media.R;
import com.neo.media.untils.BaseFragment;
import com.neo.media.untils.FragmentUtil;
import com.neo.media.untils.PhoneNumber;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.alexrs.prefs.lib.Prefs;

import static android.content.Context.MODE_PRIVATE;
import static com.neo.media.MyApplication.objPhone;


/**
 * Created by QQ on 8/16/2017.
 */

public class FragmenGiftRingtunes extends BaseFragment implements PresenterSongsImpl.View{

    public static FragmenGiftRingtunes fragment;

    public static FragmenGiftRingtunes getInstance() {
        if (fragment == null) {
            synchronized (FragmenGiftRingtunes.class) {
                if (fragment == null)
                    fragment = new FragmenGiftRingtunes();
            }
        }
        return fragment;
    }

    SharedPreferences pre;
    public String msisdn;
    public String sessionID;
    private Ringtunes objRing;
    @BindView(R.id.img_ringtunes)
    ImageView img_ringtunes;
    @BindView(R.id.txt_name_ringtunes)
    TextView txt_name_ringtunes;
    @BindView(R.id.item_singer_ringtunes)
    TextView txt_Singer;
    @BindView(R.id.txt_hansudung)
    TextView txt_hansudung;
    @BindView(R.id.item_price_ringtunes)
    TextView item_price_ringtunes;
    @BindView(R.id.switch_buyring)
    Switch switch_buyring;
    private boolean is_buyring = false;
    @BindView(R.id.img_get_contact)
    ImageView img_get_contact;
    public static TextView ed_getphone;
    PresenterSongs presenterSongs;
    @BindView(R.id.btn_gift)
    TextView btn_gift;
    @BindView(R.id.edt_loinhangui)
    EditText edt_loinhangui;
    @BindView(R.id.img_back_group)
    ImageView img_back_group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pre = getActivity().getSharedPreferences("data", MODE_PRIVATE);
/*        msisdn = pre.getString("msisdn", "");
        sessionID = pre.getString("sessionID", "");*/
        sessionID = Prefs.with(getContext()).getString("sessionID", "");
        msisdn = Prefs.with(getContext()).getString("msisdn", "");
        objRing = MyApplication.objRingGift;
        objPhone = null;


    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        ButterKnife.bind(this, view);
        presenterSongs = new PresenterSongs(this);
        ed_getphone = (TextView) view.findViewById(R.id.edt_phone_gift);
        switch_buyring.setChecked(is_buyring);
        ActivityMainHome.relative_tab.setVisibility(View.GONE);
        initDatas();
        initEvents();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initDatas() {
        Glide.with(this).load(Constant.IMAGE_URL + objRing.getImage_file()).into(img_ringtunes);
        txt_name_ringtunes.setText(objRing.getProduct_name());
        txt_Singer.setText(objRing.getSinger_name());
        txt_hansudung.setText("HSD: " + objRing.getsExpiration() + " ngày");
        item_price_ringtunes.setText(" Giá: " + objRing.getPrice() + " VNĐ");
    }

    @Override
    public void onResume() {
        super.onResume();
        // ActivityMainHome.relative_tab.setVisibility(View.GONE);
        if (objPhone != null) {
            ed_getphone.setText(objPhone.getPhoneNumber());
        } else {
            ed_getphone.setText("");
            ed_getphone.setHint("- Nhập vào số đt hoặc lấy từ danh bạ");
        }
    }

    private void initEvents() {
        img_get_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.sOption_getPhone = "GIFT";
                objPhone = null;
                Intent intent = new Intent(getActivity(), ActivityListContact.class);
                startActivity(intent);
            }
        });
        btn_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PhoneNumber.isPhoneNumber(ed_getphone.getText().toString())
                        &&PhoneNumber.StandartTelco(ed_getphone.getText().toString()).equals("VINA")
                        ) {
                    if (switch_buyring.isChecked()) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Thông báo")
                                .setContentText(" Bạn đồng ý tặng bài hát " + objRing.getProduct_name() + "" +
                                        " cho SĐT: " + PhoneNumber.convertToVnPhoneNumber(ed_getphone.getText().toString()) +
                                        " và cài đặt cho chính bạn (phí:" + objRing.getPrice() + "đ/30 ngày)")
                                .setConfirmText("Đồng ý")
                                .setCancelText("Trở lại")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        presenterSongs.addItemtoMyList(sessionID, msisdn, objRing.getsExpiration(), objRing.getId());
                                        presenterSongs.addGifttoPlayList(sessionID, msisdn, ed_getphone.getText().toString(),
                                                objRing.getsExpiration(), objRing.getId());
                                    }
                                })
                                .show();

                    } else {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Thông báo")
                                .setContentText(" Bạn đồng ý tặng bài hát " + objRing.getProduct_name() + "" +
                                        " cho SĐT: " + PhoneNumber.convertToVnPhoneNumber(ed_getphone.getText().toString()) +
                                        " (phí:" + objRing.getPrice() + "đ/30 ngày)")
                                .setConfirmText("Đồng ý")
                                .setCancelText("Trở lại")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        presenterSongs.addGifttoPlayList(sessionID, msisdn, ed_getphone.getText().toString(),
                                                objRing.getsExpiration(), objRing.getId());
                                    }
                                })
                                .show();
                    }

                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Lỗi")
                            .setContentText("Số thuê bao không gửi tặng không đúng định dạng hoặc không thuộc mạng Vinaphone")
                            .setConfirmText("Đóng")
                            .showCancelButton(true)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                // DialogUtil.showDialog(getContext(), "Lỗi", "Số thuê bao không gửi tặng không đúng");
            }
        });
        img_back_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                FragmentUtil.popBackStack(getActivity());
            }
        });
    }

    public void showGiftSongsSuccess(List<String> list) {
        if (list.size() > 0) {
            if (list.get(0).equals("0")) {
                if (edt_loinhangui.getText().toString().length() > 0) {
                    presenterSongs.api_push_notification_gift(PhoneNumber.convertTo84PhoneNunber(ed_getphone.getText().toString()),edt_loinhangui.getText().toString());
                }
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText("" + list.get(1))
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        }).show();
            } else if (list.get(0).equals("104")) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText(getResources().getString(R.string.error_104))
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
                //
                // DialogUtil.showDialog(getContext(), "Lỗi", getResources().getString(R.string.error_104));
            } else {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Thông báo")
                        .setContentText(list.get(1))
                        .setConfirmText("Đóng")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
            }
        }
    }

    @Override
    public void ShowBuySongsSuccess(List<String> list) {

    }

    @Override
    public void ShowItems(Item items) {

    }

    @Override
    public void showComment(List<Comment> listComment) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void show_add_comment_success(String s) {

    }

    @Override
    public void show_lis_songs_bysinger(List<Ringtunes> lisRingtunes) {

    }

    @Override
    public void show_lis_songs_by_id(List<Ringtunes> lisSongs) {

    }
}
