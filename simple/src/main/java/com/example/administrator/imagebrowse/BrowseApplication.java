package com.example.administrator.imagebrowse;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

/**
 * Demo的Application，初始化Fresco
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class BrowseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
