package com.m1noon.countview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

import java.text.DecimalFormat;

/**
 * View to display the number with the animation of count up or down.
 * This view is not available the layout value such as 'gravity' in order to focus on the performance of the drawing.
 */
public class CountView extends LabelView {

    private static final int DEFAULT_DURATION = 1000;
    private static final int NO_VALUE = Integer.MIN_VALUE;

    private ValueAnimator countAnimator;
    private int targetValue;
    private int currentValue;
    private int maxDuration = Integer.MAX_VALUE;
    private int velocity = NO_VALUE;
    private DecimalFormat decimalFormat;

    public CountView(Context context) {
        this(context, null);
    }

    public CountView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        countAnimator = new ValueAnimator();
        countAnimator.setDuration(DEFAULT_DURATION);
        countAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                draw((int) animation.getAnimatedValue());
            }
        });
    }

    /**
     * Set the velocity (num/sec) of the count animation.
     *
     * @param velocity velocity of the count animation. Unit is the num / sec.
     * @return CountView
     */
    public CountView velocity(int velocity) {
        if (velocity <= 0) {
            throw new IllegalArgumentException("velocity should be greater than zero.");
        }
        this.velocity = velocity;
        return this;
    }

    /**
     * Set the duration of the count animation.
     *
     * @param duration
     * @return CountView
     */
    public CountView duration(int duration) {
        countAnimator.setDuration(duration);
        velocity = NO_VALUE;
        return this;
    }

    /**
     * Set the max duration of the count animation.
     * This is only used when you called {#velocity}.
     *
     * @param maxDuration
     * @return CountView
     */
    public CountView maxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    /**
     * Set the interpolator of the count animation.
     *
     * @param interpolator
     * @return CountView
     */
    public CountView interpolator(TimeInterpolator interpolator) {
        countAnimator.setInterpolator(interpolator);
        return this;
    }

    /**
     * Set a comma-separated format.
     *
     * @return CountView
     */
    public CountView formatCommaSeparated() {
        return format("###,###");
    }

    /**
     * Set the pattern to format the number.
     *
     * @param decimalPattern
     * @return CountView
     */
    public CountView format(String decimalPattern) {
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat();
        }
        decimalFormat.applyPattern(decimalPattern);
        return this;
    }

    /**
     * Set the formatter to format the number.
     *
     * @param format
     * @return CountView
     */
    public CountView format(DecimalFormat format) {
        this.decimalFormat = format;
        return this;
    }

    /**
     * Get value.
     *
     * @return number
     */
    public int value() {
        return targetValue;
    }

    /**
     * Set the specified value with the count up (down) animation.
     *
     * @param value
     * @see #to(int, boolean)
     */
    public void to(int value) {
        to(value, true);
    }

    /**
     * Set the specified value.
     *
     * @param value   the value to set
     * @param animate set true if you need the count up (down) animation.
     */
    public void to(int value, boolean animate) {
        targetValue = value;
        if (animate) {
            startAnimation();
        } else {
            countAnimator.setIntValues(value, value);
            countAnimator.end();
            draw(value);
        }
    }

    /**
     * Count up the specified value.
     *
     * @param count value to count up
     */
    public void up(int count) {
        targetValue += count;
        startAnimation();
    }

    /**
     * Count down the specified value.
     *
     * @param count value to count down
     */
    public void down(int count) {
        targetValue -= count;
        startAnimation();
    }

    private void startAnimation() {
        countAnimator.setIntValues(currentValue, targetValue);
        if (velocity != NO_VALUE) {
            countAnimator.setDuration(Math.min(maxDuration, Math.abs((currentValue - targetValue) * 1000 / velocity)));
        }

        if (!countAnimator.isRunning() || countAnimator.getAnimatedFraction() > 0.75f) {
            countAnimator.start();
        }
    }

    private void draw(int value) {
        currentValue = value;
        if (decimalFormat == null) {
            setText(String.valueOf(currentValue));
        } else {
            setText(decimalFormat.format(currentValue));
        }
    }
}
