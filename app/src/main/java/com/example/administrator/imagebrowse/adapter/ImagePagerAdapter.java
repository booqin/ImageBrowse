package com.example.administrator.imagebrowse.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.administrator.imagebrowse.fragment.ImageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 图片查看适配器
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class ImagePagerAdapter extends FragmentPagerAdapter{

    List<Fragment> mFragments = new ArrayList<>();


    public ImagePagerAdapter(FragmentManager fm, List<String> dataSets) {
        super(fm);
        for (String dataSet : dataSets) {
            mFragments.add(ImageFragment.newInstance(dataSet));
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
}
