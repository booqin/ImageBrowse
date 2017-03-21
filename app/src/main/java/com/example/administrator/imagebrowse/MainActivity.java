package com.example.administrator.imagebrowse;

import com.example.draglayout.activity.BrowseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String[] URLS = {"http://ocvkuozgf.bkt.clouddn.com/14599544138261.png", "http://ocvkuozgf.bkt.clouddn.com/AutoLayout.png","http://ocvkuozgf.bkt.clouddn.com/LayoutParams.png","http://ocvkuozgf.bkt.clouddn.com/rect.png"};

    private SimpleDraweeView mSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.dv);

        mSimpleDraweeView.setImageURI("http://ocvkuozgf.bkt.clouddn.com/14599544138261.png");
        mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseActivity.launch(MainActivity.this, mSimpleDraweeView, URLS);
            }
        });
    }
}
