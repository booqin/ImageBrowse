package com.example.draglayout.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.draglayout.R;
import com.example.draglayout.adapter.ImagePagerAdapter;
import com.example.draglayout.fragment.ImageByPhotoViewFragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 图片浏览界面
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class BrowseActivity extends AppCompatActivity{

    public static final String TAG_W = "W";
    public static final String TAG_H = "H";
    private static String[] URLS;

    private LinearLayout mLayout;
    private ViewPager mViewPager;
    private List<String> mUrls = new ArrayList<>();

    /** 基准View的宽高值 */
    private int mTransitionViewWidth;
    private int mTransitionViewHeight;


    /**
     * 启动浏览界面
     * @param activity
     * @param transitionView 目标View，在Version大于21的时候实现共享元素
     * @param urls 图片链接
     */
    public static void launch(Activity activity, ImageView transitionView, String[] urls) {
        Intent intent = new Intent();
        intent.setClass(activity, BrowseActivity.class);
        // 这里指定了共享的视图元素
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitionView, urls[0]);
            intent.putExtra(TAG_W, transitionView.getWidth());
            intent.putExtra(TAG_H, transitionView.getHeight());
            activity.startActivity(intent, options.toBundle());
        }else {
            activity.startActivity(intent);
        }
        URLS = urls;
    }

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置一个 exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 允许使用 transitions
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new ChangeBounds());
            getWindow().setSharedElementEnterTransition(transitionSet);
            postponeEnterTransition();

            mTransitionViewWidth = getIntent().getIntExtra(TAG_W, -1);
            mTransitionViewHeight = getIntent().getIntExtra(TAG_H, -1);
        }

        setTheme(R.style.translucent);
        setContentView(R.layout.activity_image_browse);
        initUrls();
        mLayout = (LinearLayout) findViewById(R.id.ll);
        mViewPager = (ViewPager) findViewById(R.id.vp);

        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mUrls, mTransitionViewWidth, mTransitionViewHeight,
                new ImageByPhotoViewFragment.ViewPositionChangeListener() {
                    @Override
                    public void onViewPositionChanged(float scale) {
                        mLayout.setAlpha(scale);
                    }
                });

        mViewPager.setAdapter(imagePagerAdapter);
        mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return true;
            }
        });
    }

    /**
     * 初始化URL
     */
    private void initUrls(){
        for (String url : URLS) {
            mUrls.add(url);
        }
    }

    @Override
    public void setExitSharedElementCallback(SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
    }
}
