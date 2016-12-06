# Banner 控件
[![](https://jitpack.io/v/liangbx361/banner.svg)](https://jitpack.io/#liangbx361/banner)

提供通用的Banner控件，实现图片、标题、指示器展示。

## 特性
* 支持循环切换
* 支持自动播放
* 支持自定义指示器样式、显示位置

## 依赖
```groovy
compile 'com.nd.sdp.android:e-banner:0.1.3'
```

## 用法
### 基本用法
在布局文件中添加BannerView
```xml
<com.nd.sdp.android.ele.banner.BannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```
在代码中设置属性
```java
List<BannerItem> bannerItems = new ArrayList<>();
bannerItems.add(new BannerItem("http://img1.3lian.com/img13/c3/26/d/81.jpg", "girl"));
mBannerView = (BannerView) findViewById(R.id.banner);
mBannerView.setData(bannerItems);
```
默认情况下BannerView的设置：

* 自动播放（间隔时间是3秒）
  可通过`setAutoPlayMode`和`setIntervalTime`方法修改
* 循环模式（默认循环）
  可通过`setCycleMode`方法修改
* 指示器（默认圆形指示器）
  可通过`setIndicator`方法实现自定义的指示器
* 图片加载器（模式还有Glide加载图片）
  可通过`setImageLoader`方法实现自定义的图片加载器
  

## 参考
* [CycleView](https://github.com/leibing8912/LbaizxfCycleView)
* [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)
