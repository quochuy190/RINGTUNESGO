package com.neo.ringtunesgo.untils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class MD5 {
    public  String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static void main(String[] args) throws NoSuchAlgorithmException {
        
    	MD5 md5 = new MD5();
    	
    	System.out.println(md5.getMD5(""));
    	System.out.println(md5.getMD5("ll"+"4969b7be967d4e57b0d3829c46680217"+"NE"+"84912796369"+"8755"+"Toan test"+"18-04-2013 140000"+""));
    }
}