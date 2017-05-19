package com.example.draglayout;

import android.view.View;

/**
 * 位置变化接口
 *
 * @description: Created by Boqin on 2017/5/18 23:53
 * Version 1.0
 */
public interface UpdateSharedElementListener {

    View onUpdateSharedElement(int position, String url);
}
