package com.neo.ringtunesgo.View.Register;

import java.util.List;

/**
 * Created by QQ on 9/5/2017.
 */

public interface InterfaceRegisterOTP {
    interface Presenter{
        void getOTPcode(String msisdn,String sUserID );
        void xacthucOTP(String msisdn,String sUserID , String maOTP );
    }
    interface View{
        void showGetOTP(List<String> listOTP);
        void showConfirmOTP(List<String> listConfirm);
    }
}
