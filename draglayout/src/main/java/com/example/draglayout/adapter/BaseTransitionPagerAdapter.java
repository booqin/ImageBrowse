package com.example.draglayout.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/**
 * ViewPager适配器基类
 * Created by Boqin on 2017/5/18.
 * Modified by Boqin
 *
 * @Version
 */
public abstract class BaseTransitionPagerAdapter extends FragmentPagerAdapter {

    public BaseTransitionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract String getBaseName(int position);

    public abstract View getTransitionView(int position);
}
