package com.example.administrator.imagebrowse;

import com.example.draglayout.activity.BrowseActivity;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * TODO
 * Created by Boqin on 2017/4/11.
 * Modified by Boqin
 *
 * @Version
 */
public class ImageActivity extends AppCompatActivity{


    private ImageView mImageView;

    public static void launch(Activity activity, View transitionView) {
        Intent intent = new Intent();
        intent.setClass(activity, ImageActivity.class);
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
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new ChangeBounds());
//            transitionSet.addTransition(new DraweeTransition(ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER));
//            getWindow().setSharedElementEnterTransition(new ChangeBounds());
            getWindow().setSharedElementEnterTransition(transitionSet);
        }

        setContentView(R.layout.activity_image);
        mImageView = (ImageView) findViewById(R.id.iv);
//        ViewCompat.setTransitionName(mImageView, "shareName");

    }

}
