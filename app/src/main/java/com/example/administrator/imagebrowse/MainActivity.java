package com.example.administrator.imagebrowse;

import com.facebook.drawee.view.SimpleDraweeView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
//                Intent i = new Intent(v.getContext(), BrowseActivity.class);
//                startActivity(i);
                BrowseActivity.launch(MainActivity.this, mSimpleDraweeView);
            }
        });
    }
}
