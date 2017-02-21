package com.example.administrator.imagebrowse.fragment;

import com.example.administrator.imagebrowse.R;
import com.example.administrator.imagebrowse.adapter.ImagePagerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private SimpleDraweeView mDraweeView;

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
        return LayoutInflater.from(container.getContext()).inflate(R.layout.item_view_image, container, false);
    }

    @Override
    public void onViewCreated(View view,
            @Nullable
                    Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDraweeView = (SimpleDraweeView) view.findViewById(R.id.sdv);
        mDraweeView.setImageURI(mPath);

    }
}
