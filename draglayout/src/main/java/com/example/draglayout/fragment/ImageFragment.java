package com.example.draglayout.fragment;

import com.example.draglayout.DragChangedListener;
import com.example.draglayout.DragLayout;
import com.example.draglayout.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import me.relex.photodraweeview.OnScaleChangeListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 单图查看Fragment
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class ImageFragment extends Fragment{

    private static final String TAG = ImageFragment.class.getSimpleName();

    private String mPath;
    private DragLayout mDragLayout;
    private PhotoDraweeView mDraweeView;
    private ViewPositionChangeListener mViewPositionChangeListener;

    public static ImageFragment newInstance(String path, ViewPositionChangeListener viewPositionChangeListener) {
        ImageFragment newFragment = new ImageFragment();
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
        mDragLayout = (DragLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_drag_drawee, container, false);
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
        mDraweeView = (PhotoDraweeView) view.findViewById(R.id.sdv);
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(mPath);
        controller.setOldController(mDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || mDraweeView == null) {
                    return;
                }
                mDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        mDraweeView.setController(controller.build());
        mDragLayout.setDragListener(new DragChangedListener() {
            @Override
            public void onViewPositionChanged(View changedView, float scale) {
                mDraweeView.setScale(scale);
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
        mDraweeView.setOnScaleChangeListener(new OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                //屏蔽放大后的拖拽操作
                if(mDraweeView.getScale()<1.05&&mDraweeView.getScale()>0.95){
                    mDraweeView.setAllowParentInterceptOnEdge(true);
                }else {
                    mDraweeView.setAllowParentInterceptOnEdge(false);
                }
            }
        });
    }

    /**
     * 设置位置监听
     */
    public void setViewPositionChangeListener(
            ViewPositionChangeListener viewPositionChangeListener) {
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