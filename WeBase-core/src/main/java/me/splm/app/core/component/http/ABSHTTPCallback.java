package me.splm.app.core.component.http;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.splm.app.core.logic.presenter.AbsBasePresenter;


public abstract class ABSHTTPCallback<T extends AbsBasePresenter> implements HTTPCallback {

    protected T presenter;

    public ABSHTTPCallback(T t) {
        this.presenter=t;
    }

    @Override
    public void onFailure(int statusCode, Throwable e) {
        switch (statusCode) {
            case 401:
                onResponse401();
                break;
            case 403:
                onResponse403();
                break;
            case 404:
                onResponse404();
                break;
            case 405:
                onResponse405();
                break;
            case 500:
                onResponse500();
                break;
            case 503:
                onResponse503();
                break;
            default:
                onResponseFailure("状态码:"+statusCode+",提示："+e.getMessage());
        }
    }

    @Override
    public void onSuccess(JSONObject object){
        try{
            onResponse200(object);
        }catch (JSONException e){
            onResponseFailure(e.getMessage());
        }
    }

    protected void onResponse200(JSONObject object) throws JSONException{
        //TODO 需要对Response的数据进行json的解析
        int code=object.getInt(JsonObjPros.Global.CODE);
        String message=object.getString(JsonObjPros.Global.MESSAGE);
        if(code==0){
            onResponsePackedData(object);
        }else{
            onResponseFailure(message);
        }
    }
    protected void onResponse401(){
        onResponseFailure("401");
    }
    protected void onResponse403(){
        onResponseFailure("403");
    }
    protected void onResponse404(){
        onResponseFailure("404");
    }
    protected void onResponse405(){
        onResponseFailure("405");
    }
    protected void onResponse500(){
        onResponseFailure("500");
    }
    protected void onResponse503(){
        onResponseFailure("503");
    }
    protected abstract void onResponsePackedData(JSONObject object) throws JSONException;
    protected void onResponseFailure(String emsg){
        presenter.mView.onUIError(emsg);
    }
    protected List<JSONObject> resolveJsonRawListData(JSONObject object,String property) throws JSONException{
        JSONArray jsonArray=object.getJSONArray(property);
        List<JSONObject> list=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj=jsonArray.getJSONObject(i);
            list.add(obj);
        }
        return list;
    }

    protected JSONObject resolveJsonRawObjData(JSONObject object,String property){
        return null;
    }
}
