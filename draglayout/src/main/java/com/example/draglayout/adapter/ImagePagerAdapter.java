package com.example.draglayout.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.draglayout.DragChangedListener;
import com.example.draglayout.fragment.BaseTransitionFragment;
import com.example.draglayout.fragment.ImageByPhotoViewFragment;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * 图片查看适配器
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class ImagePagerAdapter extends BaseTransitionPagerAdapter {

    List<BaseTransitionFragment> mFragments = new ArrayList<>();

    public ImagePagerAdapter(FragmentManager fm, List<Uri> dataSets, boolean isShareElement, DragChangedListener dragChangedListener) {
        super(fm);
        for (Uri dataSet : dataSets) {
            mFragments.add(ImageByPhotoViewFragment.newInstance(dataSet, isShareElement, dragChangedListener));
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
    public String getBaseName(int position) {
        if (position < getCount()) {
            return mFragments.get(position).getBaseName();
        }
        return null;
    }

    @Override
    public View getTransitionView(int position) {
        return mFragments.get(position).getTransitionView();
    }
}
