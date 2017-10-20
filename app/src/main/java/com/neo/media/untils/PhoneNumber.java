package com.neo.media.untils;

import java.text.Normalizer;
import java.util.regex.Pattern;

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
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
    }
    public static boolean isPhoneNumber(String receiver) {
        boolean ok = true;
        if (receiver.length() > 12
                || receiver.length() < 9
                || (receiver.length() == 9 && !(receiver.startsWith("9") || receiver.startsWith("89") || receiver.startsWith("88") || receiver.startsWith("86")))
                || (receiver.length() == 10 && !(receiver.startsWith("09") || receiver.startsWith("089") || receiver.startsWith("088") || receiver.startsWith("086") || receiver.startsWith("1")))
                || (receiver.length() == 11 && !(receiver.startsWith("849") || receiver.startsWith("8489") || receiver.startsWith("8488") || receiver.startsWith("8486") || receiver.startsWith("01")))
                || (receiver.length() == 12 && !receiver.startsWith("841"))) {
            ok = false;
        } else {
            for (int i = 0; i < receiver.length(); i++) {
                char c = receiver.charAt(i);
                if ((c > '9') || (c < '0')) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

    public static String StandartTelco(String mobile) {
        String telconame = "";
        if (mobile.startsWith("0")) mobile = "84" + mobile.substring(1);
        else if (mobile.startsWith("1") || mobile.startsWith("9") || mobile.startsWith("89") || mobile.startsWith("88") || mobile.startsWith("86"))
            mobile = "84" + mobile;

        if (mobile.startsWith("84120") || mobile.startsWith("84121") || mobile.startsWith("84122") || mobile.startsWith("84126") || mobile.startsWith("84128") || mobile.startsWith("8490") || mobile.startsWith("8493") || mobile.startsWith("8489")) {
            telconame = "MOBI";
        } else if (mobile.startsWith("84123") || mobile.startsWith("84124") || mobile.startsWith("84125") || mobile.startsWith("84127") || mobile.startsWith("84129") || mobile.startsWith("8491") || mobile.startsWith("8494") || mobile.startsWith("8488")) {
            telconame = "VINA";
        } else if (mobile.startsWith("8416") || mobile.startsWith("8496") || mobile.startsWith("8497") || mobile.startsWith("8498") || mobile.startsWith("8486")) {
            telconame = "VT";
        } else if (mobile.startsWith("8499") || mobile.startsWith("84199")) {
            telconame = "BL";
        } else if (mobile.startsWith("84186") || mobile.startsWith("84188") || mobile.startsWith("8492")) {
            telconame = "HT";
        } else {
            telconame = "OTHER";
        }
        return telconame;
    }

}
