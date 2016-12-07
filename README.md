# Banner 控件
[![](https://jitpack.io/v/liangbx361/banner.svg)](https://jitpack.io/#liangbx361/banner)

通用的Banner控件，通过封装ViewPager实现。

![](img/banner-demo.gif)

## 特性
* 支持循环模式
* 支持自动播放（注：在手动滑动时会停止自动播放）
* 支持自定义指示器
* 支持自定义图片加载器
* 支持脏数据校验（注：只有在脏数据的情况下才进行数据刷新）

## 待支持特性
* 在不可见的情况下停止自动播放

## 依赖
```groovy
 compile 'com.github.liangbx361:banner:0.1'
```

## 用法
### 添加BannerView到布局文件

```xml
...
<com.liangbx.android.banner.BannerView
        android:id="@+id/banner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
...
```
### 在代码中设置
```java
mBannerView = (BannerView) findViewById(R.id.banner);

//设置数据
List<BannerItem> bannerItems = new ArrayList<>();
bannerItems.add(new BannerItem("http://img1.3lian.com/img13/c3/26/d/81.jpg", "bird"));
mBannerView.setData(bannerItems);

//设置点击监听事件
mBannerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(SampleActivity.this, "click position --> " + position, Toast.LENGTH_SHORT).show();
            }
        });
```
### BannerView 属性设置

| 名称                     | 默认值      | 说明         |
| ---------------------- | -------- | ---------- |
| setAutoPlayMode        | 默认自动播放模式 | 设置自动播放模式   |
| setIntervalTime        | 默认间隔3秒   | 设置自动播放间隔时间 |
| setCycleMode           | 默认循环模式   | 设置循环模式     |
| setOnItemClickListener | 默认无      | 设置点击监听事件   |
| setIndicator           | 默认无      | 设置指示器      |
| setImageLoader         | 默认无      | 设置图片加载器    |

### Indicator
考虑到每个项目的Indicator样式和动画都不相同，所以需要外部实现`Indicator`接口。
这里推荐一个开源项目[PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)。
示例代码如下：
```java
public class CycleIndicator implements Indicator {

    private int mSize;
    private PageIndicatorView mIndicatorView;

    public CycleIndicator(int size) {
        mSize = size;
    }

    @Override
    public View getLayout(Context context, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.banner_indicator, parent, false);
        mIndicatorView = (PageIndicatorView) view.findViewById(R.id.indicator);
        mIndicatorView.setCount(mSize);
        return view;
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM | Gravity.CENTER;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels, BannerItem bannerItem) {
        mIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position, BannerItem bannerItem) {
        mIndicatorView.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mIndicatorView.onPageScrollStateChanged(state);
    }
}
```

### ImageLoader
考虑到每个项目可能都有自己的图片加载库，第三方开源的库也有很多（Glide、Fresco等）。如果默认提供一个图片加载库会造成可能不是项目用的，无故增加包大小，所以不提供默认实现。交由外部实现`ImageLoader`接口。
这里使用Glide实现的示例：
```java
public class GlideImageLoader implements ImageLoader {

    @Override
    public void onLoad(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.defautl_image)
                .error(R.mipmap.defautl_image)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    @Override
    public void onUnLoad(ImageView imageView) {

    }
}
```

## 参考
* [CycleView](https://github.com/leibing8912/LbaizxfCycleView)
* [PageIndicatorView](https://github.com/romandanylyk/PageIndicatorView)
