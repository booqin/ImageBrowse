package com.example.draglayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.example.draglayout.fragment.BaseTransitionFragment;
import com.example.draglayout.fragment.ImageByPhotoViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片查看适配器
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class ImagePagerAdapter extends BaseTransitionPagerAdapter {

    List<BaseTransitionFragment> mFragments = new ArrayList<>();

    public ImagePagerAdapter(FragmentManager fm, List<String> dataSets, int w, int h, ImageByPhotoViewFragment.ViewPositionChangeListener viewPositionChangeListener) {
        super(fm);
        for (String dataSet : dataSets) {
            mFragments.add(ImageByPhotoViewFragment.newInstance(dataSet, w, h, viewPositionChangeListener));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public String getTransitionName(int position) {
        if (position < getCount()) {
            return mFragments.get(position).getTransitionName();
        }
        return null;
    }

    @Override
    public View getTransitionView(int position) {
        return mFragments.get(position).getTransitionView();
    }
}
