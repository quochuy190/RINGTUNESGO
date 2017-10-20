package com.neo.media.Fragment.Album.Presenter;

import com.neo.media.ApiService.ApiService;
import com.neo.media.Fragment.Album.View.FragmentAlbum;
import com.neo.media.Listener.CallbackData;
import com.neo.media.Model.Album;

import java.util.ArrayList;

/**
 * Created by QQ on 7/8/2017.
 */

public class PresenterAlbum implements PresenterAlbumImpl {
    ApiService apiSeviceAlbumIml;
    FragmentAlbum fragmentAlbum;

    public PresenterAlbum(FragmentAlbum fragmentAlbum) {
        this.fragmentAlbum = fragmentAlbum;
        apiSeviceAlbumIml = new ApiService();
    }

    @Override
    public void getallElement(String page, String index) {
        fragmentAlbum.showDialogLoading();
        String Service = "album_album_service";
        String Provider = "default";
        String ParamSize = "3";

        apiSeviceAlbumIml.getAlbum(new CallbackData<Album>() {
            @Override
            public void onGetDataSuccess(ArrayList<Album> arrayList) {
                fragmentAlbum.showAllAlbum(arrayList);
                fragmentAlbum.hideDialogLoading();
            }

            @Override
            public void onGetDataFault(Exception e) {

            }

            @Override
            public void onGetObjectDataSuccess(Album Object) {

            }
        }, Service, Provider, ParamSize, page, index);

    }
}
