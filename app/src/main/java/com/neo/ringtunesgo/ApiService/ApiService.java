package com.neo.ringtunesgo.ApiService;

import android.util.Log;

import com.google.gson.Gson;
import com.neo.ringtunesgo.CRBTModel.CLI;
import com.neo.ringtunesgo.CRBTModel.CLIS;
import com.neo.ringtunesgo.CRBTModel.GROUP;
import com.neo.ringtunesgo.CRBTModel.GROUPS;
import com.neo.ringtunesgo.CRBTModel.Info_User;
import com.neo.ringtunesgo.CRBTModel.Item;
import com.neo.ringtunesgo.CRBTModel.PROFILE;
import com.neo.ringtunesgo.CRBTModel.PROFILES;
import com.neo.ringtunesgo.CRBTModel.subscriber;
import com.neo.ringtunesgo.Config.Constant;
import com.neo.ringtunesgo.Listener.CallbackData;
import com.neo.ringtunesgo.Model.Album;
import com.neo.ringtunesgo.Model.Banner;
import com.neo.ringtunesgo.Model.Comment;
import com.neo.ringtunesgo.Model.MetadataString;
import com.neo.ringtunesgo.Model.Return;
import com.neo.ringtunesgo.Model.Ringtunes;
import com.neo.ringtunesgo.Model.Singer;
import com.neo.ringtunesgo.Model.Topic;
import com.neo.ringtunesgo.Model.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neo.ringtunesgo.Config.Constant.USER_ID;

/**
 * Created by QQ on 8/4/2017.
 */

public class ApiService {
    ApiSeviceImpl apiSevice;
    ApiServicePostImp apiServicePostImp;


    // get_phone_3g
    public void login_3g(final CallbackData<String> callbackData, String userid) {
        apiServicePostImp = ApiServicePostImp.retrofitpost.create(ApiServicePostImp.class);
        Call<ResponseBody> call = apiServicePostImp.api_get_3g(userid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> list = new ArrayList<String>();
                    String jsonString = response.body().string();
                    Log.i("abc", jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String error = jsonObject.getString("ERROR");
                    String result = jsonObject.getString("RESULT");
                    String msisdn = jsonObject.getString("MSISDN");
                    String message = jsonObject.getString("MESSAGE");
                    list.add(error);
                    list.add(result);
                    list.add(msisdn);
                    list.add(message);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // Api get All Album
    public void getAlbum(final CallbackData<Album> callbackData, String Service, String Provider,
                         String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getAlbum = apiSevice.getAlbum(Service, Provider, ParamSize, P1, P2, USER_ID);
        getAlbum.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    ArrayList<Album> list = Album.getAllAlbum(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    // get Comment
    public void getComment(final CallbackData<Comment> callbackData, String Service, String Provider,
                           String ParamSize, String P1, String P2, String P3, String P4, String P5, String UserID) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getComment(Service, Provider, ParamSize, P1, P2, P3, P4, P5, UserID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Comment> list = Comment.getListEntity(jsonString);
                    Log.i("a bc", "" + list.size());
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    // log action user
    public void logInfo_ActionUser(final CallbackData<String> callbackData, String Service, String Provider,
                                   String ParamSize, String P1, String P2, String P3, String P4, String P5) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);

        Call<ResponseBody> call = apiSevice.getservice(Service, Provider, ParamSize, P1, P2, P3, P4, P5);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> listReturn = new ArrayList<String>();
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    Return obj = gson.fromJson(jsonString, Return.class);
                    listReturn.add(obj.getResult());
                    callbackData.onGetDataSuccess(listReturn);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // get Ringtunes by Topic
    public void getRingtunes_ByTocpic(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                      String ParamSize, String P1, String P2, String P3, String P4) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.getRingtunesByTopic(Service, Provider, ParamSize, P1, P2, P3, P4);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Ringtunes> list = Ringtunes.getListEntity(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get All Ringtunes hot
    public void getRingtunesHot(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getAllRingtunesHot(Service, Provider, ParamSize, P1, P2, P3, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Ringtunes> list = Ringtunes.getListEntity(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get All rank ringtunes
    public void getrankRingtunes(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                 String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P3_SQL(Service, Provider, ParamSize, P1, P2, P3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Ringtunes> list = Ringtunes.getListEntity(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get All Ringtunes By Album, Type, Singer
    public void getRingtunes_ByAlbum(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                     String ParamSize, String P1, String P2, String P3, String P4) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getRingtun_ByAlbum(Service, Provider, ParamSize, P1, P2, P3, P4, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Ringtunes> list = Ringtunes.getListEntity(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // Api search Ringtunes
    public void getSearchRingtunes(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                   String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.search_Ringtunes(Service, Provider, ParamSize, P1, P2, P3, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Ringtunes> list = Ringtunes.getListEntity(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get all Singer
    public void getAllSinger(final CallbackData<Singer> callbackData, String Service, String Provider, String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getAllSinger(Service, Provider, ParamSize, P1, P2, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Singer> list = Singer.getLisElement(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get Detail Singer by Id
    public void getDetail_Singer(final CallbackData<Singer> callbackData, String Service, String Provider,
                                 String ParamSize, String P1) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getDetailSinger(Service, Provider, ParamSize, P1, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    ArrayList<Singer> list = Singer.getLisElement(jsonString);
                    Log.i("abc", "" + list.size());
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    // get all Tocpic
    public void getAllTopic(final CallbackData<Topic> callbackData, String Service, String Provider,
                            String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getAlbum = apiSevice.getAllSinger(Service, Provider, ParamSize, P1, P2, Constant.USER_ID);

        getAlbum.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Topic> list = Topic.getList(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //get all type
    public void getAllType(final CallbackData<Type> callbackData, String Service, String Provider,
                           String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getAllSinger(Service, Provider, ParamSize, P1, P2, Constant.USER_ID);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    ArrayList<Type> list = Type.getLisElement(jsonString);
                    callbackData.onGetDataSuccess(list);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataFault(new Exception(t));
            }
        });
    }

    //
    public void add_Group(final CallbackData<String> callbackData, String Service, String Provider,
                          String ParamSize, String sesionID, String msisdn, String groupname, String caller_msisdn) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.addGroup(Service, Provider, ParamSize, sesionID, msisdn, groupname, caller_msisdn);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> success = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    success.add(Error);
                    success.add(ERROR_DESC);
                    if (Error.equals("0")) {
                        JSONObject jsonGroups = new JSONObject(jsonObj.getString("GROUPS"));
                        String id_group = new JSONObject(jsonGroups.getString("GROUP")).getString("id");
                        success.add(id_group);
                    }
                    callbackData.onGetDataSuccess(success);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // get all group
    public void getAllGroup(final CallbackData<GROUPS> callbackData, String Service, String Provider,
                            String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.getAllGroup(Service, Provider, ParamSize, P1, P2, P3);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    GROUPS objGroups = new GROUPS();
                    Gson gson = new Gson();
                    ArrayList<GROUP> lisGroup = new ArrayList<GROUP>();
                    String num_of_clis;
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject objGrop = new JSONObject(jsonObj.getString("GROUPS"));
                    String totalGorup = objGrop.getString("total");
                    int itotalGroup = Integer.parseInt(totalGorup);
                    if (itotalGroup == 0) {
                        objGroups.setTotal("0");
                        callbackData.onGetObjectDataSuccess(objGroups);
                    } else if (itotalGroup == 1) {
                        JSONObject jsonGroup = new JSONObject(objGrop.getString("GROUP"));
                        num_of_clis = jsonGroup.getString("num_of_clis");
                        int i_num_of_clis = Integer.parseInt(num_of_clis);
                        if (i_num_of_clis > 1) {
                            GROUP group = gson.fromJson(objGrop.getString("GROUP"), GROUP.class);
                            objGroups.setTotal("" + itotalGroup);
                            lisGroup.add(group);
                            objGroups.setGroup(lisGroup);
                            callbackData.onGetObjectDataSuccess(objGroups);
                        } else {
                            JSONObject jsonCLIS = new JSONObject(jsonGroup.getString("CLIS"));
                            String id_group = jsonGroup.getString("id");
                            String name = jsonGroup.getString("name");
                            String type = jsonGroup.getString("id");
                            String content_id = jsonGroup.getString("content_id");
                            CLI cli = gson.fromJson(jsonCLIS.getString("CLI"), CLI.class);
                            List<CLI> listCli = new ArrayList<CLI>();
                            listCli.add(cli);
                            GROUP objGr = new GROUP();
                            CLIS clis = new CLIS();
                            clis.setCLI(listCli);
                            objGr.setClis(clis);
                            objGr.setContent_id(content_id);
                            objGr.setId(id_group);
                            objGr.setType(type);
                            objGr.setName(name);
                            objGroups.setTotal("" + itotalGroup);
                            lisGroup.add(objGr);
                            objGroups.setGroup(lisGroup);
                            callbackData.onGetObjectDataSuccess(objGroups);
                        }
                    } else if (itotalGroup > 1) {
                        JSONArray Jarray = objGrop.getJSONArray("GROUP");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            num_of_clis = object.getString("num_of_clis");
                            int i_num_of_clis = Integer.parseInt(num_of_clis);
                            if (i_num_of_clis > 1) {
                                GROUP group = gson.fromJson(object.toString(), GROUP.class);
                                objGroups.setTotal("" + itotalGroup);
                                lisGroup.add(group);
                            } else {
                                JSONObject jsonCLIS = new JSONObject(object.getString("CLIS"));
                                String id_group = object.getString("id");
                                String name = object.getString("name");
                                String type = object.getString("id");
                                String content_id = object.getString("content_id");
                                CLI cli = gson.fromJson(jsonCLIS.getString("CLI"), CLI.class);
                                List<CLI> listCli = new ArrayList<CLI>();
                                listCli.add(cli);
                                GROUP objGr = new GROUP();
                                CLIS clis = new CLIS();
                                clis.setCLI(listCli);
                                objGr.setClis(clis);
                                objGr.setContent_id(content_id);
                                objGr.setId(id_group);
                                objGr.setType(type);
                                objGr.setName(name);
                                objGroups.setTotal("" + itotalGroup);
                                lisGroup.add(objGr);
                            }
                        }
                        objGroups.setGroup(lisGroup);
                        callbackData.onGetObjectDataSuccess(objGroups);
                    }

                    /*String test = fundial_profile.getFundial_profile().getRply().getName();
                    GROUP GROUP = fundial_profile.getFundial_profile().getRply().getSubscriber().getGroups().getGroup();*/
                    //items = sgroups.getGroup();
                    //  callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // delete group
    public void deleteGroup(final CallbackData<String> callbackData, String Service, String Provider,
                            String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.deleteGroup(Service, Provider, ParamSize, P1, P2, P3);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> items = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    items.add(error);
                    items.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // add cli to group by group
    public void add_cli_to_group(final CallbackData<CLI> callbackData, String Service, String Provider,
                                 String ParamSize, String sesionID, String msisdn, String groupID, final String caller_msisdn
    ) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.add_cli_to_group(Service, Provider, ParamSize, sesionID, msisdn, groupID,
                caller_msisdn);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<CLI> list = new ArrayList<CLI>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject jsonGroups = new JSONObject(jsonObj.getString("GROUPS"));
                    JSONObject jsonGroup = new JSONObject(jsonGroups.getString("GROUP"));
                    JSONObject jsonCLIS = new JSONObject(jsonGroup.getString("CLIS"));
                    String[] array = caller_msisdn.split(",");
                    Gson gson = new Gson();
                    int count_phone = array.length;
                    if (count_phone > 1) {
                        JSONArray Jarray = jsonCLIS.getJSONArray("CLI");
                        if (Jarray.length() > 1) {
                            CLIS clis = gson.fromJson(jsonGroup.getString("CLIS"), CLIS.class);
                            list.addAll(clis.getCLI());
                        }
                    } else {
                        CLI clis = gson.fromJson(jsonCLIS.getString("CLI"), CLI.class);
                        list.add(clis);
                    }
                    callbackData.onGetDataSuccess(list);
                    // callbackData.onGetObjectDataSuccess(clis);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // delete cli to group
    public void delete_cli_to_group(final CallbackData<CLI> callbackData, String Service, String Provider,
                                    String ParamSize, String sesionID, String msisdn, String groupID, String caller_msisdn) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.delete_cli_to_group(Service, Provider, ParamSize, sesionID, msisdn, groupID, caller_msisdn);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject jsonGroups = new JSONObject(jsonObj.getString("GROUPS"));
                    JSONObject jsonGroup = new JSONObject(jsonGroups.getString("GROUP"));
                    JSONObject jsonCLIS = new JSONObject(jsonGroup.getString("CLIS"));
                    Gson gson = new Gson();
                    CLI clis = gson.fromJson(jsonCLIS.getString("CLI"), CLI.class);
                    callbackData.onGetObjectDataSuccess(clis);

                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //get all conllection
    public void getAllConllection(final CallbackData<Item> callbackData, String Service, String Provider,
                                  String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.getAllConllection(Service, Provider, ParamSize, P1, P2);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Item> items = new ArrayList<Item>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject jsonItems = new JSONObject(jsonObj.getString("ITEMS"));
                    JSONArray jsonArray = jsonItems.getJSONArray("ITEM");
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Item item = new Item();
                        item = gson.fromJson(jsonArray.getJSONObject(i).toString(), Item.class);
                        items.add(item);
                    }
                    Log.i("abc", "" + items.size());

                    // String test = fundial_profile.getFundial_profile().getRply().getName();
                    //Log.i(TAG, test);
                    // Item item = fundial_profile.getFundial_profile().getRply().getSubscriber().getItems().getObjItem();

                    callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //get all conllection
    public void get_info_songs_collection(final CallbackData<Ringtunes> callbackData, String Service, String Provider,
                                  String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.api_get_P2_sql(Service, Provider, ParamSize, P1, P2);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Ringtunes> items = new ArrayList<Ringtunes>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    Gson gson = new Gson();

                    JSONArray jsonObj = new JSONArray(jsonString);
                    for (int i = 0;i<jsonObj.length();i++){
                        Ringtunes objRing = gson.fromJson(jsonObj.getJSONObject(i).toString(), Ringtunes.class);
                        items.add(objRing);
                    }
                    callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // delete item from mylist
    public void delete_item_from_mylist(final CallbackData<String> callbackData, String Service, String Provider,
                                        String ParamSize, String sesionID, String msisdn, String item_id, String userId
    ) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.delete_cli_to_mylist(Service, Provider, ParamSize, sesionID, msisdn, item_id,
                userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> lisError = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String ERROR = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    lisError.add(ERROR);
                    lisError.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(lisError);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // delete item from mylist
    public void log_info_charge_service(final CallbackData<String> callbackData, String Service, String Provider,
                                        String ParamSize, String P1, String P2, String P3, String P4, String P5,String P6, String P7, String P8
    ) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_log_info(Service, Provider, ParamSize, P1, P2, P3,P4, P5, P6, P7, P8);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> lisError = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String ERROR = jsonObj.getString("RESULT");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    lisError.add(ERROR);
                    lisError.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(lisError);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // thêm luật phát nhạc
    public void add_Profiles(final CallbackData<String> callbackData, String Service, String Provider, String ParamSize,
                             String session_id, String msisdn, String content_id, String caller_type, String caller_id, String from_time,
                             String to_time) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.add_Profiles(Service, Provider, ParamSize, session_id, msisdn, content_id,
                caller_type, caller_id, from_time, to_time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> lisError = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String ERROR = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    lisError.add(ERROR);
                    lisError.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(lisError);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //  update luật phát nhạc
    public void update_Profiles(final CallbackData<String> callbackData, String Service, String Provider, String ParamSize,
                                String session_id, String msisdn, String id_profile, String content_id, String caller_type, String caller_id, String from_time,
                                String to_time) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.update_Profiles(Service, Provider, ParamSize, session_id, msisdn, id_profile, content_id,
                caller_type, caller_id, from_time, to_time);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> lisError = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String ERROR = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    lisError.add(ERROR);
                    lisError.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(lisError);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // get content item - lấy thông tin chi tiết bài hát
    public void getContenItem(final CallbackData<Item> callbackData, String Service, String Provider,
                              String ParamSize, String sesionID, String msisdn, String content_id) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.getContentItem(Service, Provider, ParamSize, sesionID, msisdn, content_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Item> items = new ArrayList<Item>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    Gson gson = new Gson();
                    JSONObject jsonItems = new JSONObject(jsonObj.getString("ITEMS"));
                    // Meta_FUNDIAL_PROFILE fundial_ofapi = gson.fromJson(jsonString, Meta_FUNDIAL_PROFILE.class);
                    //  Log.i("abc", fundial_ofapi.getFundial_profile().getRply().getError());
                    Item item = gson.fromJson(jsonItems.getString("ITEM"), Item.class);
                    callbackData.onGetObjectDataSuccess(item);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // add item to list ringtunes
    public void addItemToMyList(final CallbackData<String> callbackData, String Service, String Provider,
                                String ParamSize, String sesionID, String msisdn, String expiration, String content_id) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.addItemtoMyList(Service, Provider, ParamSize, sesionID, msisdn, expiration, content_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> items = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    String error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");

                    items.add(error);
                    items.add(ERROR_DESC);

                    callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    //3.20	ADD_GIFT_TO_PLAYLIST/tặng bài hát
    public void addGiftToPlayList(final CallbackData<String> callbackData, String Service, String Provider,
                                  String ParamSize, String P1, String P2, String P3, String P4, String P5) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.addGifttoPlayList(Service, Provider, ParamSize, P1, P2, P3, P4, P5);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> items = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    String error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");

                    items.add(error);
                    items.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(items);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    //API  Comment lên hệ thống
    public void addComment(final CallbackData<String> callbackData, String Service, String Provider,
                           String ParamSize, String P1, String P2, String P3, String P4, String P5, String P6, String P7,
                           String P8, String P9, String P10, String P11, String P12) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.addComment(Service, Provider, ParamSize, P1, P2, P3, P4, P5, P6, P7,
                P8, P9, P10, P11, P12);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String obj = "";
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    MetadataString string = gson.fromJson(jsonString, MetadataString.class);
                    obj = string.getReturn1();
                    callbackData.onGetObjectDataSuccess(obj);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    //3.10	GET_SUBSCRIBER_DETAILS/lấy thông tin thuê bao
    public void get_SUBSCRIBER_DETAILS(final CallbackData<subscriber> callbackData, String Service, String Provider,
                                       String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.get_SUBSCRIBER_DETAILS(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    subscriber subscriber = new subscriber();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    Gson gson = new Gson();
                    subscriber = gson.fromJson(jsonString, com.neo.ringtunesgo.CRBTModel.subscriber.class);
                    callbackData.onGetObjectDataSuccess(subscriber);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    //USER_INIT/init thông tin user theo sdt
    public void init_service(final CallbackData<String> callbackData, String Service, String Provider,
                             String ParamSize, String P1, String P2, String P3, String P4, String P5) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.init_service(Service, Provider, ParamSize, P1, P2, P3, P4, P5);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String obj = "";
                    String jsonString = response.body().string();
                    JSONObject jsonObj = new JSONObject(jsonString);
                    obj = jsonObj.getString("return");
                    Log.i("abc", obj);
                    callbackData.onGetObjectDataSuccess(obj);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }
    public void update_token(final CallbackData<String> callbackData, String Service, String Provider,
                             String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P2_sql_value(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String obj = "";
                    String jsonString = response.body().string();
                    JSONObject jsonObj = new JSONObject(jsonString);
                    obj = jsonObj.getString("return");
                    Log.i("abc", obj);
                    callbackData.onGetObjectDataSuccess(obj);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }
    // lấy luật phát nhac
    public void getAllProfile(final CallbackData<PROFILE> callbackData, String Service, String Provider,
                              String ParamSize, String P1, String P2, String P3, String P4) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> getElement = apiSevice.api_get_P4_CRBT(Service, Provider, ParamSize, P1, P2, P3, P4);

        getElement.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<PROFILE> profiles = new ArrayList<PROFILE>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);

                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONObject jsonProfiles = new JSONObject(jsonObj.getString("PROFILES"));
                    Gson gson = new Gson();
                    int total = Integer.parseInt(jsonProfiles.getString("total"));
                    if (total <= 0) {
                        callbackData.onGetDataSuccess(profiles);
                    } else if (total == 1) {
                        PROFILE profile = gson.fromJson(jsonProfiles.getString("PROFILE"), PROFILE.class);
                        profiles.add(profile);
                        callbackData.onGetDataSuccess(profiles);
                    } else if (total > 1) {
                        PROFILES profiles1 = gson.fromJson(jsonObj.getString("PROFILES"), PROFILES.class);
                        profiles.addAll(profiles1.getProfile());
                        callbackData.onGetDataSuccess(profiles);
                    }
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // Xoá luật phát nhac
    public void api_delete_profile(final CallbackData<String> callbackData, String Service, String Provider,
                                   String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P3_CRBT(Service, Provider, ParamSize, P1, P2, P3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    arrayList.add(Error);
                    arrayList.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // DELETE_SUBSCRIBER - Huỷ dịch vụ
    public void api_delete_subcriber(final CallbackData<String> callbackData, String Service, String Provider,
                                     String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P2_CRBT(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    arrayList.add(Error);
                    arrayList.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // tiếp tục or đăng ký dịch vụ
    public void api_update_subcriber(final CallbackData<String> callbackData, String Service, String Provider,
                                     String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P3_CRBT(Service, Provider, ParamSize, P1, P2, P3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String ERROR_DESC = jsonObj.getString("ERROR_DESC");
                    arrayList.add(Error);
                    arrayList.add(ERROR_DESC);
                    callbackData.onGetDataSuccess(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // DELETE_SUBSCRIBER - Huỷ dịch vụ
    public void api_login(final CallbackData<String> callbackData, String Service, String Provider,
                          String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P2_CRBT(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(12, jsonString.length() - 3);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String MESSAGE = jsonObj.getString("MESSAGE");
                    String SESSIONID = jsonObj.getString("RESULT");
                    Error = Error.replace(" ", "");
                    MESSAGE = MESSAGE.replace(" ", "");
                    SESSIONID = SESSIONID.replace(" ", "");
                    arrayList.add(Error);
                    arrayList.add(MESSAGE);
                    arrayList.add(SESSIONID);
                    callbackData.onGetDataSuccess(arrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // lấy mã otp
    public void api_get_code_otp(final CallbackData<String> callbackData, String Service, String Provider,
                                 String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P2_CRBT(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(12, jsonString.length() - 3);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String error = jsonObj.getString("ERROR");
                    error = error.replace(" ", "");
                    String message = jsonObj.getString("MESSAGE");
                    message = message.replace(" ", "");
                    String result = jsonObj.getString("RESULT");
                    message = message.replace(" ", "");
                    arrayList.add(error);
                    arrayList.add(message);
                    arrayList.add(result);
                    callbackData.onGetDataSuccess(arrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // xac thực mã otp
    public void api_confirm_otp(final CallbackData<String> callbackData, String Service, String Provider,
                                String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P3_CRBT(Service, Provider, ParamSize, P1, P2, P3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(12, jsonString.length() - 3);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String error = jsonObj.getString("ERROR");
                    String message = jsonObj.getString("MESSAGE");
                    String result = jsonObj.getString("RESULT");
                    error = error.replace(" ", "");
                    message = message.replace(" ", "");
                    result = result.replace(" ", "");
                    arrayList.add(error);
                    arrayList.add(message);
                    arrayList.add(result);
                    callbackData.onGetDataSuccess(arrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // get banner header
    //USER_INIT/init thông tin user theo sdt
    public void api_header_slides_service(final CallbackData<Banner> callbackData, String Service, String Provider,
                                          String ParamSize, String P1) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P1_sql(Service, Provider, ParamSize, P1);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Banner> lisBanner = new ArrayList<Banner>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(11, jsonString.length() - 2);
                    JSONArray jsonArray = new JSONArray(jsonString);
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Banner banner = new Banner();
                        banner = gson.fromJson(jsonArray.getJSONObject(i).toString(), Banner.class);
                        lisBanner.add(banner);
                    }
                    callbackData.onGetDataSuccess(lisBanner);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    // login vinaportal
    public void api_login_vinaportal(final CallbackData<String> callbackData, String Service, String Provider,
                                     String ParamSize, String P1, String P2, String P3) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P3_CRBT(Service, Provider, ParamSize, P1, P2, P3);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(12, jsonString.length() - 3);
                    System.out.print(jsonString);
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("ERROR");
                    String MESSAGE = jsonObj.getString("MESSAGE");
                    String pass = jsonObj.getString("RESULT");
                    Error = Error.replace(" ", "");
                    MESSAGE = MESSAGE.replace(" ", "");
                    pass = pass.replace(" ", "");
                    arrayList.add(Error);
                    arrayList.add(MESSAGE);
                    arrayList.add(pass);
                    callbackData.onGetDataSuccess(arrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    public void api_get_infouser(final CallbackData<Info_User> callbackData, String Service, String Provider,
                                 String ParamSize, String P1, String P2) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P2_sql(Service, Provider, ParamSize, P1, P2);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Info_User objInfo = new Info_User();
                    String jsonString = response.body().string();
                    jsonString = jsonString.replaceAll("\\\\", "");
                    jsonString = jsonString.substring(12, jsonString.length() - 3);
                    JSONObject jsonObject= new JSONObject(jsonString);
                    String sHoten = jsonObject.getString("HOTEN");
                    String sMobile = jsonObject.getString("MOBILE");
                    String sSUB_REGISTED = jsonObject.getString("SUB_REGISTED");
                    String sSEX = jsonObject.getString("SEX");
                    String sBIRTHDAY = jsonObject.getString("BIRTHDAY");
                    objInfo.setHOTEN(sHoten);
                    objInfo.setSEX(sSEX);
                    objInfo.setBIRTHDAY(sBIRTHDAY);
                    objInfo.setSUB_REGISTED(sSUB_REGISTED);

                    callbackData.onGetObjectDataSuccess(objInfo);
                  //  callbackData.onGetDataSuccess(lisBanner);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //callbackData.onGetDataFault(t);
            }
        });
    }

    public void api_update_infouser(final CallbackData<String> callbackData, String Service, String Provider,
                                 String ParamSize, String P1, String P2, String P3, String P4, String P5, String P6) {
        apiSevice = ApiSeviceImpl.retrofit.create(ApiSeviceImpl.class);
        Call<ResponseBody> call = apiSevice.api_get_P6_SQL_value(Service, Provider, ParamSize, P1, P2, P3, P4, P5, P6);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String jsonString = response.body().string();
                    JSONObject jsonObj = new JSONObject(jsonString);
                    String Error = jsonObj.getString("return");
                    callbackData.onGetObjectDataSuccess(Error);
                } catch (Exception e) {
                    callbackData.onGetDataFault(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
