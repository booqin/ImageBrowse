# 前言
　　在新项目中，涉及到图片的查看，浏览，这里设计了一个仿微信朋友圈的图片查看器，将简要介绍其实现。项目地址[仿微信朋友圈图片查看](https://github.com/booqin/ImageBrowse)
# 功能点
　　先看以下朋友圈的图片查看功能：

![circle](https://github.com/booqin/ImageBrowse/raw/master/capture/circle.gif)

　　主要功能点有以下三个
- 图片拖拽时缩小，背景透明，一定阈值时关闭该页
- 图片缩放
- 转场动画的实现

实现以上功能，我们需要一个透明的Activity，来显示完整的图片，拖拽功能通过继承一个LinearLayout来实现一个DragLayout，在该类完成了对子类的拖拽管理，图片的缩放功能基于第三方开源[PhotoDraweeView](https://github.com/ongakuer/PhotoDraweeView)，而整个图片加载框架使用Fresco完成。

![draglayout](https://github.com/booqin/ImageBrowse/raw/master/capture/draglayout.gif)

## 设置透明Activity
　　通过在res中的style文件下添加透明主题
```java
    <style name="translucent" parent="Theme.AppCompat.NoActionBar">
        <item name="android:windowBackground">@color/translucent_background</item>
        <item name="android:windowIsTranslucent">true</item>
    </style>
```
　　在对应的配置清单Activity中添加该主题
```java
        <activity android:name=".BrowseActivity"
            android:theme="@style/translucent"/>
```

## 拖拽
　　在DragLayout中，由于涉及到拖拽，缩小等功能，我们需要关注用户的手势变化，手势的管理通过GestureDetector和ViewDragHelper工具类完成。同时图片查看过程中还有放大，多图切换的功能，涉及到手势冲突，因此需要重写onInterceptTouchEvent方法类进行事件的拦截。
```java
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        //DragHelper必须要持有一个DOWN事件，不然后续的操作无效
        if (action == MotionEvent.ACTION_DOWN) {
            mDragHelper.processTouchEvent(ev);
        }
        return mGestureDetector.onTouchEvent(ev) ;
    }
```
其中mGestureDetector会检测是否为下滑动作，然后拦截。同时提供了DragChangedListener接口，该接口会在位置变化时被调用，以及手势释放View时：

```java
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

```

## 图片缩放
　　涉及到网络图片的加载功能，整个图片加载框架使用Fresco，一般情况下只要使用SimpleDraweeView即可，而涉及到图片缩放功能，github上开源了一个继承于SimpleDraweeView的PhotoDraweeView，该View对图片的缩放做了很好的支持。在使用过程遇到无法缩小的情况，查看源码，发现作者屏蔽了缩小功能：
```java
        if (draweeView == null || scale < mMinScale || scale > mMaxScale) {
            return;
        }
```
简单修改如下：
```java
        //移除对缩小操作的屏蔽
        if (draweeView == null || scale > mMaxScale) {
            return;
        }
```
这样我们就可以在拖拽的过程中缩小图片了。

## 转场动画
　　todo