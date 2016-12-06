package com.liangbx.android.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.liangbx.android.banner.adapter.BannerAdapter;
import com.liangbx.android.banner.listener.OnDispatchTouchEvent;
import com.liangbx.android.banner.listener.OnItemClickListener;
import com.liangbx.android.banner.model.BannerItem;
import com.liangbx.android.banner.util.BannerItemComparator;
import com.liangbx.android.banner.util.PositionUtil;
import com.liangbx.android.banner.widget.TouchViewPager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * <p>Title: <／p>
 * <p>Description: TODO 可以考虑Banner没有显示的情况下，停止自动播放<／p>
 * <p>Copyright: Copyright (c) 2016<／p>
 * <p>Company: NetDragon<／p>
 *
 * @author liangbx
 * @version 2016/12/1
 */
public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String TAG = "BannerView";
    private static final boolean DEBUG = false;

    private Params mParams = new Params();
    private OnItemClickListener mListener;
    private ImageLoader mImageLoader;
    private Indicator mIndicator;

    private TouchViewPager mViewPager;
    private BannerAdapter mAdapter;
    private LinkedList<BannerItem> mBannerItems = new LinkedList<>();
    private BannerItemComparator mBannerItemsComparator;
    private Subscription mAutoPlayModeSubscription;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        if(DEBUG) {
            Log.d(TAG, "BannerView --> init");
        }

        mViewPager = new TouchViewPager(getContext());
        // 设置滚动监听
        mViewPager.addOnPageChangeListener(this);
        // 监听到触摸事件后，停止自动播放
        mViewPager.setListener(new OnDispatchTouchEvent() {
            @Override
            public void onDispatchTouchEvent(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(mParams.autoPlayMode) {
                            stopAutoPlayInternal();
                        }
                        if(DEBUG) {
                            Log.d(TAG, "ViewPager onDispatch --> ACTION_DOWN");
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        if(mParams.autoPlayMode) {
                            stopAutoPlayInternal();
                            startAutoPlayInternal();
                        }
                        if(DEBUG) {
                            Log.d(TAG, "ViewPager onDispatch --> ACTION_UP");
                        }
                        break;
                }
            }
        });

        //添加Banner
        addView(mViewPager, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mBannerItemsComparator = new BannerItemComparator();

        if(!mParams.cycleMode) {
            mParams.initPosition = 0;
        }
    }

    /**
     * 设置Banner数据，只有脏数据才重新触发刷新
     */
    public void setData(List<BannerItem> bannerItems) {
        LinkedList<BannerItem> newBannerItems = new LinkedList<>(bannerItems);
        if(isSupportCycleMode()) {
            fillCycleModeData(newBannerItems);
        }

        if(!mBannerItemsComparator.isEqual(mBannerItems, newBannerItems)) {
            mBannerItems.clear();
            mBannerItems.addAll(bannerItems);
            refresh();
        }
    }

    public void refresh() {
        if(isSupportCycleMode()) {
            fillCycleModeData(mBannerItems);
        }

        LinkedList<ImageView> views = new LinkedList<>();
        for (BannerItem bannerItem : mBannerItems) {
            ImageView imageView = new ImageView(getContext());
            views.add(imageView);
        }

        mAdapter = new BannerAdapter(views, mBannerItems, mParams.cycleMode, mListener, mImageLoader);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mParams.initPosition);
    }

    /**
     * 启动自动轮播
     */
    public void startAutoPlay() {
        mParams.autoPlayMode = true;
        startAutoPlayInternal();
    }

    private void startAutoPlayInternal() {
        mAutoPlayModeSubscription = rx.Observable.interval(mParams.intervalTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //FIXME mAdapter可能为空
                        if(mAdapter != null && mParams.autoPlayMode) {
                            int position = mViewPager.getCurrentItem();
                            position++;
                            position %= mAdapter.getCount();
                            mViewPager.setCurrentItem(position, true);
                        }
                    }
                });

        Log.d(TAG, "startAutoPlayInternal");
    }

    /**
     * 停止自动轮播
     */
    public void stopAutoPlay() {
        mParams.autoPlayMode = false;
        stopAutoPlayInternal();
    }

    private void stopAutoPlayInternal() {
        if(mAutoPlayModeSubscription != null && !mAutoPlayModeSubscription.isUnsubscribed()) {
            mAutoPlayModeSubscription.unsubscribe();
            mAutoPlayModeSubscription = null;

            Log.d(TAG, "stopAutoPlayInternal");
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int realPosition;

        if(mParams.cycleMode) {
            realPosition = PositionUtil.getRealPosition(position, mAdapter.getCount());
        } else {
            realPosition = position;
        }

        if(mIndicator != null) {
            mIndicator.onPageScrolled(realPosition, positionOffset, positionOffsetPixels,
                    mBannerItems.get(position));
        }

        if(DEBUG) {
            Log.d(TAG, "onPageScrolled --> position -->" + position + "| realPosition -->" + realPosition);
        }
    }

    @Override
    public void onPageSelected(int position) {
        int realPosition;

        if(mParams.cycleMode) {
            realPosition = PositionUtil.getRealPosition(position, mAdapter.getCount());
        } else {
            realPosition = position;
        }

        if(mIndicator != null) {
            mIndicator.onPageSelected(realPosition, mBannerItems.get(position));
        }

        if(DEBUG) {
            Log.d(TAG, "onPageSelected --> position -->" + position + "| realPosition -->" + realPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 循环模式需要处理头尾部分
        if(state == ViewPager.SCROLL_STATE_IDLE && mParams.cycleMode) {
            if(mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                mViewPager.setCurrentItem(1, false);
            } else if(mViewPager.getCurrentItem() == 0) {
                mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 2, false);
            }
        }

        if(mIndicator != null) {
            mIndicator.onPageScrollStateChanged(state);
        }
    }

    private void fillCycleModeData(LinkedList<BannerItem> bannerItems) {
        if(bannerItems.size() > 0) {
            BannerItem firstItem = bannerItems.getFirst();
            BannerItem lastItem = bannerItems.getLast();
            bannerItems.addFirst(lastItem);
            bannerItems.addLast(firstItem);
        }
    }

    private static class Params {
        // 播放间隔时间, 默认3秒
        long intervalTime = 3000;
        // 初始的起始位置
        int initPosition = 1;
        // 自动播放模式
        boolean autoPlayMode = true;
        // 循环模式
        boolean cycleMode = true;
        // 默认图片
        int defaultImage;
    }

    public void setIntervalTime(long time) {
        mParams.intervalTime = time;
    }

    public void setCycleMode(boolean cycleMode) {
        mParams.cycleMode = cycleMode;
    }

    public void setAutoPlayMode(boolean autoPlayMode) {
        mParams.autoPlayMode = autoPlayMode;
    }

    public void setDefaultImage(@DrawableRes int defaultImage) {
        mParams.defaultImage = defaultImage;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    public void setIndicator(Indicator indicator) {
        mIndicator = indicator;

        // 移除之前的指示器
        if(hasIndicator()) {
            removeViewAt(1);
        }

        // 添加指示器
        if(mIndicator != null) {
            View view = mIndicator.getLayout(getContext(), this);
            ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
            if(viewLayoutParams != null) {
                addView(view, new FrameLayout.LayoutParams(
                        viewLayoutParams.width, viewLayoutParams.height, mIndicator.getGravity()
                ));
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private boolean hasIndicator() {
        return getChildCount() >= 2;
    }

    private boolean isSupportCycleMode() {
        return mParams.cycleMode && mBannerItems.size() > 1;
    }
}

