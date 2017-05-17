package com.example.administrator.imagebrowse;

import com.bumptech.glide.Glide;
import com.example.draglayout.activity.BrowseActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * TODO
 * Created by Boqin on 2017/5/16.
 * Modified by Boqin
 *
 * @Version
 */
public class MainActivity extends AppCompatActivity{


    private static final String[] URLS = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png", "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/LayoutParams.png","http://ocvkuozgf.bkt.clouddn.com/rect.png"};

    private ImageView mImageView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.iv);

        mButton = (Button) findViewById(R.id.button);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseActivity.launch(MainActivity.this, mImageView, URLS, 1, "http://ocvkuozgf.bkt.clouddn.com/14599544138261.png");
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.get(MainActivity.this).clearMemory();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MainActivity.this).clearDiskCache();
                    }
                }).start();

            }
        });

        Glide.with(this)
                .load("http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png")
                .into(mImageView);
    }
}
