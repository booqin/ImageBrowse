package com.example.draglayout.fragment;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.example.draglayout.DragChangedListener;
import com.example.draglayout.DragLayout;
import com.example.draglayout.R;
import com.example.draglayout.activity.BrowseActivity;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * 大图浏览界面
 * Created by Boqin on 2017/5/11.
 * Modified by Boqin
 *
 * @Version
 */
public class ImageByPhotoViewFragment extends BaseTransitionFragment {

    private static final String TAG = ImageByPhotoViewFragment.class.getSimpleName();

    private PhotoView mPhotoView;
    private String mPath;
    private DragLayout mDragLayout;
    private ImageByPhotoViewFragment.ViewPositionChangeListener mViewPositionChangeListener;

    private int mOverrideWidth;
    private int mOverrideHeight;

    public static ImageByPhotoViewFragment newInstance(String path, int width, int height,
            ImageByPhotoViewFragment.ViewPositionChangeListener viewPositionChangeListener) {
        ImageByPhotoViewFragment newFragment = new ImageByPhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, path);
        bundle.putInt(BrowseActivity.TAG_W, width);
        bundle.putInt(BrowseActivity.TAG_H, height);
        newFragment.setArguments(bundle);
        newFragment.setViewPositionChangeListener(viewPositionChangeListener);

        return newFragment;

    }

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mPath = args.getString(TAG);
            mOverrideWidth = args.getInt(BrowseActivity.TAG_W);
            mOverrideHeight = args.getInt(BrowseActivity.TAG_H);
        } else {
            mPath = "";
            mOverrideWidth = -1;
            mOverrideHeight = -1;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable
                    ViewGroup container,
            @Nullable
                    Bundle savedInstanceState) {
        mDragLayout = (DragLayout) LayoutInflater.from(getContext()).inflate(R.layout.fragment_image_drag_photo, container, false);
        mDragLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mDragLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        return mDragLayout;
    }

    @Override
    public void onViewCreated(View view,
            @Nullable
                    Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (PhotoView) view.findViewById(R.id.photo_view);
        mPhotoView.setScaleLevels(0.5f, 1f, 2f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhotoView.setTransitionName(mPath);
        }
        setGlide();

        mDragLayout.setDragListener(new DragChangedListener() {
            @Override
            public void onViewPositionChanged(View changedView, float scale) {
                if (scale > mPhotoView.getMaximumScale()) {
                    mPhotoView.setScale(mPhotoView.getMaximumScale());
                } else if (scale < mPhotoView.getMinimumScale()) {
                    mPhotoView.setScale(mPhotoView.getMinimumScale());
                } else {
                    mPhotoView.setScale(scale);
                }

                if (mViewPositionChangeListener != null) {
                    mViewPositionChangeListener.onViewPositionChanged(scale);
                }
            }

            @Override
            public boolean onViewReleased() {
                getActivity().onBackPressed();
                return true;
            }
        });
        mPhotoView.setOnScaleChangeListener(new OnScaleChangedListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                //屏蔽放大后的拖拽操作
                if (mPhotoView.getScale() < 1.05 && mPhotoView.getScale() > 0.95) {
                    mPhotoView.setAllowParentInterceptOnEdge(true);
                } else {
                    mPhotoView.setAllowParentInterceptOnEdge(false);
                }
            }
        });
    }

    /**
     * 设置位置监听
     */
    public void setViewPositionChangeListener(
            ImageByPhotoViewFragment.ViewPositionChangeListener viewPositionChangeListener) {
        mViewPositionChangeListener = viewPositionChangeListener;
    }

    @Override
    public String getTransitionName() {
        return mPath;
    }

    @Override
    public View getTransitionView() {
        return mPhotoView;
    }

    private void setGlide() {
        //获取缩略图，使用指定大小，同时关闭动画，这样才能利用ActivityA中的缓存图达到元素共享的效果
        if (mOverrideWidth > 0 && mOverrideHeight > 0) {
            DrawableRequestBuilder<?> thumb = Glide.with(this).load(mPath).dontAnimate().override(mOverrideWidth, mOverrideHeight);
            Glide.with(this).load(mPath).dontAnimate().thumbnail(thumb).into(mPhotoView);
        } else {
            Glide.with(this).load(mPath).into(mPhotoView);
        }

    }

    public interface ViewPositionChangeListener {
        /**
         * 当位置发生变化时回调
         */
        void onViewPositionChanged(float scale);
    }

}
