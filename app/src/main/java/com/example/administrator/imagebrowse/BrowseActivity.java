package com.example.administrator.imagebrowse;

import java.util.ArrayList;
import java.util.List;

import com.example.administrator.imagebrowse.adapter.ImagePagerAdapter;
import com.example.administrator.imagebrowse.fragment.ImageFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * 图片浏览界面
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class BrowseActivity extends AppCompatActivity{

    private static final String[] URLS = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png", "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/LayoutParams.png","http://ocvkuozgf.bkt.clouddn.com/rect.png"};

    private ViewPager mViewPager;
    private List<String> mUrls = new ArrayList<>();

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browse);
        initUrls();
        mViewPager = (ViewPager) findViewById(R.id.vp);

        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mUrls);
        mViewPager.setAdapter(imagePagerAdapter);
    }

    private void initUrls(){
        for (String url : URLS) {
            mUrls.add(url);
        }
    }
}
