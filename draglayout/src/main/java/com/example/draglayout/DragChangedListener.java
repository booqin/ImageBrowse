package com.example.draglayout;

import android.view.View;

/**
 * 位置变化接口
 *
 * @description: Created by Boqin on 2017/2/22 17:18
 */
public interface DragChangedListener {

    /**
     * 当目标View改变时
     *
     * @param changedView 目标View
     * @param scale       缩放比
     */
    void onViewPositionChanged(View changedView, float scale);

    /**
     * 当拖拽事件结束时
     *
     * @return true:消耗事件 <p> false:不消耗事件
     */
    boolean onViewReleased();
}
