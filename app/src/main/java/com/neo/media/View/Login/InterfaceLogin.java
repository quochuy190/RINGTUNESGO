package com.neo.media.View.Login;

import com.neo.media.CRBTModel.subscriber;

import java.util.List;

/**
 * Created by QQ on 9/5/2017.
 */

public interface InterfaceLogin {
    interface Presenter{

        void LoginVinaphonePortal(String username, String password, String userId);
        void Login(String username, String pass);
        void get_detail_subsriber(String sesionID, String msisdn);

    }
    interface View{
        void showInfo_User(subscriber subscriber);
        void showDataLogin(List<String> list);
        void showDataLoginVinaphonePortal(List<String> list);
    }
}
