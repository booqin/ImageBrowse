package com.example.administrator.imagebrowse;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.draglayout.UpdateSharedElementListener;
import com.example.draglayout.activity.BrowseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo
 * Created by Boqin on 2017/5/16.
 * Modified by Boqin
 *
 * @Version
 */
public class MainActivity extends AppCompatActivity{


    private static final String[] URLS = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png", "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/LayoutParams.png","http://ocvkuozgf.bkt.clouddn.com/rect.png"};

    private ImageView mImageView1;
    private ImageView mImageView2;
    private Button mButton;
    private List<Uri> mUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrlList = new ArrayList<>();

        mImageView1 = (ImageView) findViewById(R.id.iv1);
        mImageView2 = (ImageView) findViewById(R.id.iv2);

        mButton = (Button) findViewById(R.id.button);

        for (String path : URLS) {
            mUrlList.add(Uri.parse(path));
        }

        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseActivity.launchWithShareElement(MainActivity.this, mImageView1, mUrlList, 1, new UpdateSharedElementListener() {
                    @Override
                    public View onUpdateSharedElement(int position, String url) {
                        return position==1?mImageView1:mImageView2;
                    }
                });
            }
        });

        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BrowseActivity.launchWithShareElement(MainActivity.this, mImageView2, mUrlList, 0);
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
                .into(mImageView1);
        Glide.with(this)
                .load("http://ocvkuozgf.bkt.clouddn.com/14599544138261.png")
                .into(mImageView2);
    }
}
