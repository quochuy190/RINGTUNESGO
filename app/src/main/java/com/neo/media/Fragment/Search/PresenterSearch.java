package com.neo.media.Fragment.Search;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.KeyWord;
import com.neo.media.Model.Ringtunes;

import java.util.ArrayList;

/**
 * Created by QQ on 11/30/2017.
 */

public class PresenterSearch  {
    ApiService apiService;
    FragmentSearchNew fragmentSearchNew;

    public PresenterSearch( FragmentSearchNew fragmentSearchNew) {
        apiService = new ApiService();
        this.fragmentSearchNew = fragmentSearchNew;
    }


    public void getSearch(String key,String type, String page, String index, String user_id) {
        String Service = "search_main_service2";
        String Provider = "default";
        String ParamSize = "5";

        apiService.api_search_main_service(new CallbackData<Ringtunes>() {
            @Override
            public void onGetDataSuccess(ArrayList<Ringtunes> arrayList) {
                fragmentSearchNew.showListSearch(arrayList);
                //viewSearch.showListSearch(arrayList);
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Ringtunes Object) {

            }
        }, Service, Provider, ParamSize, key, type, page, index, user_id);
    }

    public void search_keyword_top(String userid) {
        //fragmentSearchNew.showDialogLoading();
        String Service = "search_keyword_top";
        String Provider = "default";
        String ParamSize = "1";
        apiService.api_search_keyword_top(new CallbackData<KeyWord>() {
            @Override
            public void onGetDataSuccess(ArrayList<KeyWord> arrayList) {
                fragmentSearchNew.hideDialogLoading();
                if (arrayList.size()>0){
                    fragmentSearchNew.show_search_keyword(arrayList);
                }else {

                }
            }

            @Override
            public void onGetDataFault(Exception e) {
                fragmentSearchNew.hideDialogLoading();
            }

            @Override
            public void onGetObjectDataSuccess(KeyWord Object) {
                fragmentSearchNew.hideDialogLoading();
            }
        }, Service, Provider, ParamSize, userid);
    }
}
