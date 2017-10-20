package com.neo.media.Listener;

import java.util.ArrayList;

/**
 * Created by QQ on 7/4/2017.
 */

public interface CallbackData<T> {
    void onGetDataSuccess(ArrayList<T> arrayList);
    void onGetDataFault(Exception e);
    void onGetObjectDataSuccess(T Object);
}
