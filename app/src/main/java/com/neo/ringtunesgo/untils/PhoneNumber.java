package com.neo.ringtunesgo.untils;

/**
 * Created by QQ on 7/17/2017.
 */

public class PhoneNumber {
    public static String convertTo84PhoneNunber(String phoneNumber){
        //remove all space
        phoneNumber = phoneNumber.replaceAll("\\s", "");
        if(phoneNumber.startsWith("0")){
            return  "84"+phoneNumber.substring(1);
        }
        return phoneNumber;
    }
    public static String convertToVnPhoneNumber(String phoneNumber){
        phoneNumber = phoneNumber.replaceAll("\\s", "");
        phoneNumber= phoneNumber.replaceAll("\\+", "");
        if(phoneNumber.startsWith("84")){
            return  "0"+phoneNumber.substring(2);
        }
        return phoneNumber;
    }

}
