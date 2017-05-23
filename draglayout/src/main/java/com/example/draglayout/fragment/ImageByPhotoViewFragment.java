package com.example.draglayout.fragment;

import com.bumptech.glide.Glide;
import com.example.draglayout.DragChangedListener;
import com.example.draglayout.DragLayout;
import com.example.draglayout.R;
import com.example.draglayout.activity.BrowseActivity;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import android.net.Uri;
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
    private Uri mPath;
    private DragLayout mDragLayout;
    private DragChangedListener mDragChangedListener;
    private boolean mIsShareElement;


    public static ImageByPhotoViewFragment newInstance(Uri path, boolean isShareElement,
            DragChangedListener dragChangedListener) {
        ImageByPhotoViewFragment newFragment = new ImageByPhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, path);
        bundle.putBoolean(BrowseActivity.TAG_SHARE_ELEMENT, isShareElement);
        newFragment.setArguments(bundle);
        newFragment.setDragChangedListener(dragChangedListener);

        return newFragment;

    }

    @Override
    public void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mPath = args.getParcelable(TAG);
            mIsShareElement = args.getBoolean(BrowseActivity.TAG_SHARE_ELEMENT);
        } else {
            mIsShareElement = false;
        }
        if(mPath==null){
            mPath = Uri.EMPTY;
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
        mPhotoView.setScaleLevels(1f, 1.5f, 2f);
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

                if (mDragChangedListener!=null) {
                    mDragChangedListener.onViewPositionChanged(changedView, scale);
                }
            }

            @Override
            public boolean onViewReleased() {
                if (mDragChangedListener!=null) {
                    return mDragChangedListener.onViewReleased();
                }
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


    @Override
    public String getBaseName() {
        return mPath.getPath();
    }

    @Override
    public View getTransitionView() {
        return mPhotoView;
    }

    /**
     * 设置拖拽监听
     */
    public void setDragChangedListener(DragChangedListener dragChangedListener) {
        mDragChangedListener = dragChangedListener;
    }

    private void setGlide() {
        //获取缩略图，使用指定大小，同时关闭动画，这样才能利用ActivityA中的缓存图达到元素共享的效果
        if (mIsShareElement) {
//            DrawableRequestBuilder<?> thumb = Glide.with(this).load(mPath).override(mOverrideWidth, mOverrideHeight);
//            Glide.with(this).load(mPath).override(mOverrideWidth, mOverrideHeight).dontAnimate().into(mPhotoView);
            Glide.with(this).load(mPath).thumbnail(0.1f).dontAnimate().into(mPhotoView);

        } else {
            Glide.with(this).load(mPath).into(mPhotoView);
        }

    }

}
