package com.example.administrator.imagebrowse;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.Window;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * TODO
 * Created by Boqin on 2017/4/12.
 * Modified by Boqin
 *
 * @Version
 */
public class PhotoDraweeViewActivity extends AppCompatActivity{

    private static final String[] URLS = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png", "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/LayoutParams.png","http://ocvkuozgf.bkt.clouddn.com/rect.png"};

    private PhotoDraweeView mPhotoDraweeView;


    public static void launch(Activity activity, View transitionView) {
        Intent intent = new Intent();
        intent.setClass(activity, PhotoDraweeViewActivity.class);
        // 这里指定了共享的视图元素
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, transitionView, "shareName");
            activity.startActivity(intent, options.toBundle());
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
            ChangeBounds changeBounds = new ChangeBounds();
            //            getWindow().setSharedElementEnterTransition(new ChangeBounds());
            getWindow().setSharedElementEnterTransition(changeBounds);
        }

        setContentView(R.layout.activity_photo);
        String uriString = URLS[0];
        Uri uri = Uri.parse(uriString);
        mPhotoDraweeView = (PhotoDraweeView) findViewById(R.id.photo);
        mPhotoDraweeView.setPhotoUri(uri);
    }
}
