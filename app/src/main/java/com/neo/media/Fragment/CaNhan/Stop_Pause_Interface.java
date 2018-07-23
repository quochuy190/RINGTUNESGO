package com.neo.media.Fragment.CaNhan;

import com.neo.media.CRBTModel.subscriber;

import java.util.List;

/**
 * Created by QQ on 7/27/2017.
 */

public interface Stop_Pause_Interface {
    interface Presenter{
        void get_detail_subsriber(String sesionID, String msisdn);
        void stop_Service(String sessionID, String msisdn);
        void pause_resume_subscriber(String sessionID, String msisdn, String status);
        void change_stype_phone(String input);
        void  add_subcriber(String sessionID, String msisdn, String PKG_CODE);
    }
    interface View{
        void show_stop_service(List<String> list);
        void show_resue_service(List<String> list);
        void show_add_service(List<String> list);
        void showInfo_User(subscriber subscriber);
    }
}
