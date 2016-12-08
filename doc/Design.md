# 公共控件之Banner设计实现
## 背景
Banner是一种横幅式广告又称旗帜广告，在移动端的应用中是非常常见的布局。
Banner在移动端一般拥有如下特点：
* 展示一组图片
* 带有图片位置指示器
* 可自动播放
* 可循环

我们要实现一个Banner的功能需求，通常是使用ViewPager作为图片显示容器，但是ViewPager只能处理图片的显示以及图片切换的转场动画，有写特性需要自动考虑如何实现。
* 如何支持循环
* 如何实现自动播放
* 如何处理触摸事件和自动播放事件的冲突问题

Banner这样一个常用的功能，完全可以统一成一个公共的控件，隐藏内部的实现细节。方便项目快速接入，来达到快速开发的目的。
## 前期调研
GitHub上已经

## 思路
### 如何实现循环模式？
我都知道ViewPager本身是不支持循环模式的（PS：要是原生就支持该多好啊，我们就不用费那么大劲自己实现了。），使用需要通过特殊的技巧来实现。

* 改造Banner数据 
需要对Banner的数据列表进行改造，在头部添加尾部的数据、在尾部添加头部的数据，然后将数据设置到Adapter中。
* 处理越界的情况
当滑动到头部时，实际显示的是最后一张图片，这时候我们需要切换当前位置到倒数第2的位置，即最后一张图显示的位置，而且不能被用户感知到的你切换操作。
在ViewPager中我们可以通在`onPageScrollStateChanged`的空闲状态执行操作，调用`setCurrentItem(1, false)`方法可以让用户无感知切换操作。


### 如何实现自动播放？

## 设计
! [](doc/img/domain.png)

* BannerView 作为Banner的显示容器，包含Banner的展示、切换、自动播放的控制等功能。
* BannerItem Banner的Model，为BannerView提供显示数据
* Indicator 负责为BannerView提供指示器支持
* ImageLoader 负责为BannerView提供Banner图片加载支持

## 实现步骤
### BannerView

### Indicator

### ImageLoader



