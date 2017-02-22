package com.example.administrator.imagebrowse.fragment;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.imagebrowse.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * TODO
 * Created by Boqin on 2017/2/21.
 * Modified by Boqin
 *
 * @Version
 */
public class ImageFragment extends Fragment{

    private static final String TAG = ImageFragment.class.getSimpleName();

    private String mPath;
    private PhotoDraweeView mDraweeView;

    public static ImageFragment newInstance(String path) {
        ImageFragment newFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, path);
        newFragment.setArguments(bundle);

        //bundle还可以在每个标签里传送数据

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
        //
//        return LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_drawee, container, false);
//        return LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_layout, container, false);
        return LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_drag_drawee, container, false);
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

    }
}
