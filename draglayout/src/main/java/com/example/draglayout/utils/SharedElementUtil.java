package com.example.draglayout.utils;

import com.example.draglayout.bean.TransitionBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * TODO
 * Created by Boqin on 2017/5/19.
 * Modified by Boqin
 *
 * @Version
 */
public class SharedElementUtil {

    public static String getTransitionName(String url, int position){
        Gson gson = new Gson();
        TransitionBean transitionBean = new TransitionBean();
        transitionBean.setUrl(url);
        transitionBean.setPosition(position);
        return gson.toJson(transitionBean, TransitionBean.class);
    }

    public static TransitionBean getTransitionBean(String jsonStr){
        Gson gson = new Gson();
        try {
            TransitionBean transitionBean = gson.fromJson(jsonStr, TransitionBean.class);
            return transitionBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
