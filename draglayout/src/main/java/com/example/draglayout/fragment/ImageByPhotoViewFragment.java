package com.example.draglayout.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.example.draglayout.DragChangedListener;
import com.example.draglayout.DragLayout;
import com.example.draglayout.R;
import com.github.chrisbanes.photoview.PhotoView;

import com.github.chrisbanes.photoview.OnScaleChangedListener;

/**
 * TODO
 * Created by Boqin on 2017/5/11.
 * Modified by Boqin
 *
 * @Version
 */
public class ImageByPhotoViewFragment extends Fragment {

    private static final String TAG = ImageByPhotoViewFragment.class.getSimpleName();

    private PhotoView mPhotoView;
    private String mPath;
    private DragLayout mDragLayout;
    private ImageByPhotoViewFragment.ViewPositionChangeListener mViewPositionChangeListener;

    public static ImageByPhotoViewFragment newInstance(String path, ImageByPhotoViewFragment.ViewPositionChangeListener viewPositionChangeListener) {
        ImageByPhotoViewFragment newFragment = new ImageByPhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, path);
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
        mPath = args != null ? args.getString(TAG) : "";

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState) {
        mDragLayout = (DragLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_image_drag_photo, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDragLayout.setTransitionName(mPath);
        }
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


        Glide.with(this).load(mPath).into(mPhotoView);

        mDragLayout.setDragListener(new DragChangedListener() {
            @Override
            public void onViewPositionChanged(View changedView, float scale) {
                if (scale>mPhotoView.getMaximumScale()) {
                    mPhotoView.setScale(mPhotoView.getMaximumScale());
                }else if(scale<mPhotoView.getMinimumScale()){
                    mPhotoView.setScale(mPhotoView.getMinimumScale());
                }else {
                    mPhotoView.setScale(scale);
                }

                if (mViewPositionChangeListener!=null) {
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
                if(mPhotoView.getScale()<1.05&&mPhotoView.getScale()>0.95){
                    mPhotoView.setAllowParentInterceptOnEdge(true);
                }else {
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

    public interface ViewPositionChangeListener {
        /**
         * 当位置发生变化时回调
         * @param scale
         */
        void onViewPositionChanged(float scale);
    }

}
