package com.artfonapps.hotelstask.speedviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * Created by paperrose on 15.07.2016.
 */
public class SpeedViewPager extends ViewPager {
    public SpeedViewPager(Context context) {
        super(context);
        initSpeedViewPager();
    }

    public SpeedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSpeedViewPager();
    }

    private SpeedScroller scroller = null;

    private void initSpeedViewPager() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            scroller = new SpeedScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollSpeed(float scrollSpeed) {
        scroller.setScrollSpeed(scrollSpeed);
    }
}
