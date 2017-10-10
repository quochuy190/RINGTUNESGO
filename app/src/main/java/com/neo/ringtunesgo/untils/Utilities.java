package com.neo.ringtunesgo.untils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by QQ on 7/11/2017.
 */

public class Utilities {
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    private static final float BLUR_RADIUS = 25f;

    public static Bitmap blur(Bitmap image, Context context) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(context);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

//Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        //Chuyển đổi thời gian sang định dạng Hours:Minutes:Seconds
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        //Thêm giờ nếu có
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        //Thêm vào số 0 nếu có 1 chữ số
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        //kết quả trả về
        return finalTimerString;
    }

    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        //Tính toán tỉ lệ %
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        //trả về giá trị %
        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        //trả về thời gian hiện tại trong mili giây
        return currentDuration * 1000;
    }


}
