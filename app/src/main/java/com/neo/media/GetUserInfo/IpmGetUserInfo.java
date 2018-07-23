package com.neo.media.GetUserInfo;

import com.neo.media.CRBTModel.subscriber;

import java.util.List;

/**
 * Created by QQ on 4/4/2018.
 */

public interface IpmGetUserInfo {
    interface Presenter{
        void api_get_detail_subsriber(String sesionID, String msisdn);
        void api_subsriber_sms(String msisdn, String m_click);
        void login_3g(String userid);
        void Login(String username, String pass);
    }
    interface View{
        void show_detail_subsriber(subscriber objSub);
        void show_error_api();
        void show_login_3g(List<String> list);
        void showDataLogin(List<String> list);
    }
}
