package com.neo.media.Fragment.InfoUser;

import com.neo.media.CRBTModel.Info_User;

/**
 * Created by QQ on 10/5/2017.
 */

public interface InfoUserInterface {
    interface Presenter{
        void getInfoUser(String msisdn, String userid);
        void update_profile(String name, String msisdn, String datereg, String sex, String brithday, String userid );
    }
    interface View{
        void showGetInfoUser(Info_User objInfo);
        void showUpdateInfo(String listString);
    }
}
