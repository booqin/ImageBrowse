package com.example.draglayout.activity;

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
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.draglayout.R;
import com.example.draglayout.UpdateSharedElementListener;
import com.example.draglayout.adapter.ImagePagerAdapter;
import com.example.draglayout.bean.TransitionBean;
import com.example.draglayout.fragment.ImageByPhotoViewFragment;
import com.example.draglayout.utils.SharedElementUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public static final String TAG_POSITION = "POSITION";
    public static final String TAG_PATH = "PATH";

    private LinearLayout mLayout;
    private ViewPager mViewPager;
    private List<String> mUrls;

    /** 基准View的宽高值 */
    private int mTransitionViewWidth;
    private int mTransitionViewHeight;

    private int mPosition;
    private ImagePagerAdapter mImagePagerAdapter;

    /**
     * 启动浏览界面
     * @param activity
     * @param transitionView 目标View，在Version大于21的时候实现共享元素
     * @param urls 图片链接
     * @param position 当前显示位置
     * @param thumbnailUrl 缩略图
     */
    public static void launch(Activity activity, final ImageView transitionView, List<String> urls, int position, String thumbnailUrl) {
        launch(activity, transitionView, urls, position, new UpdateSharedElementListener() {
            @Override
            public View onUpdateSharedElement(int position, String url) {
                return transitionView;
            }
        });
    }

    /**
     * 启动浏览界面
     * @param activity
     * @param transitionView 目标View，在Version大于21的时候实现共享元素
     * @param urls 图片链接
     * @param position 当前显示位置
     */
    public static void launch(Activity activity, final View transitionView, final List<String> urls, int position, final UpdateSharedElementListener updateSharedElementListener) {
        Intent intent = new Intent();
        intent.setClass(activity, BrowseActivity.class);
        // 这里指定了共享的视图元素
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitionView, SharedElementUtil.getTransitionName(urls.get(position), position));
            intent.putExtra(TAG_W, transitionView.getWidth());
            intent.putExtra(TAG_H, transitionView.getHeight());
            intent.putExtra(TAG_POSITION, position);
            String[] paths = new String[urls.size()];
            urls.toArray(paths);
            intent.putExtra(TAG_PATH, paths);
            activity.startActivity(intent, options.toBundle());
            activity.setExitSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    super.onMapSharedElements(names, sharedElements);
                    if (sharedElements.size()!=0||names.size()==0) {
                        return;
                    }
                    if (updateSharedElementListener!=null) {
                        sharedElements.clear();
                        TransitionBean transitionBean = SharedElementUtil.getTransitionBean(names.get(0));
                        if (transitionBean!=null) {
                            sharedElements.put(names.get(0), updateSharedElementListener.onUpdateSharedElement(transitionBean.getPosition(), transitionBean.getUrl()));
                        }
                    }else {
                        sharedElements.clear();
                        sharedElements.put(names.get(0), transitionView);
                    }
                }
            });
        }else {
            activity.startActivity(intent);
        }
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
            transitionSet.addTransition(new ChangeImageTransform());
            getWindow().setSharedElementEnterTransition(transitionSet);
            postponeEnterTransition();

            setEnterSharedElementCallback(new SharedElementCallback() {

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    super.onMapSharedElements(names, sharedElements);
                    sharedElements.clear();
                    sharedElements.put(SharedElementUtil.getTransitionName(mImagePagerAdapter.getBaseName(mViewPager.getCurrentItem()), mViewPager.getCurrentItem()), mImagePagerAdapter.getTransitionView(mViewPager.getCurrentItem()));
                }
            });
        }

        initIntentData();

        setTheme(R.style.translucent);
        setContentView(R.layout.activity_image_browse);
        mLayout = (LinearLayout) findViewById(R.id.ll);
        mViewPager = (ViewPager) findViewById(R.id.vp);

        mImagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), mUrls, mTransitionViewWidth, mTransitionViewHeight,
                new ImageByPhotoViewFragment.ViewPositionChangeListener() {
                    @Override
                    public void onViewPositionChanged(float scale) {
                        mLayout.setAlpha(scale);
                    }
                });

        mViewPager.setAdapter(mImagePagerAdapter);
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
        mViewPager.setCurrentItem(mPosition);


    }

    private void initIntentData() {
        mTransitionViewWidth = getIntent().getIntExtra(TAG_W, -1);
        mTransitionViewHeight = getIntent().getIntExtra(TAG_H, -1);
        mPosition = getIntent().getIntExtra(TAG_POSITION, 0);
        String[] paths = getIntent().getStringArrayExtra(TAG_PATH);
        mUrls = new ArrayList<>();
        for (String path : paths) {
            mUrls.add(path);
        }
    }
}
