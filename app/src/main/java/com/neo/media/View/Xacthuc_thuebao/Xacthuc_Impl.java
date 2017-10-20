package com.neo.media.View.Xacthuc_thuebao;

import com.neo.media.CRBTModel.subscriber;

import java.util.List;

/**
 * Created by QQ on 8/31/2017.
 */

public interface Xacthuc_Impl {
    interface Presenter{
        void login_3g(String userid);
        void Login(String username, String pass);
        void get_detail_subsriber(String sesionID, String msisdn);
    }
    interface View{
        void show_login_3g(List<String> list);
        void showInfo_User(subscriber subscriber);
        void showDataLogin(List<String> list);
    }

}
