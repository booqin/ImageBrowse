package com.example.draglayout.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment基类
 * Created by Boqin on 2017/5/18.
 * Modified by Boqin
 *
 * @Version
 */
public abstract class BaseTransitionFragment extends Fragment{

    /**
     * 转换的标签名
     */
    public abstract String getTransitionName();
    /**
     * 转换的基准View
     */
    public abstract View getTransitionView();
}
