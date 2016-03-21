package com.m1noon.countview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * This is a lightweight process than {@link android.widget.TextView}.
 * This view is not available the layout value such as 'gravity' in order to focus on the performance of the drawing.
 * <p/>
 * This is based on the class of the android-apidemos.
 * <p/>
 * see https://github.com/appium/android-apidemos/blob/master/src/io/appium/android/apis/view/LabelView.java
 */
public class LabelView extends View {
    private Paint textPaint;
    private String text;
    private int ascent;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);

        final float density = context.getResources().getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LabelView);
        CharSequence s = a.getString(R.styleable.LabelView_android_text);
        setText(s == null ? null : s.toString());
        textPaint.setColor(a.getColor(R.styleable.LabelView_android_textColor, 0xFF000000));
        textPaint.setTextSize(a.getDimension(R.styleable.LabelView_android_textSize, 16 * density));
        setPadding(
                a.getDimensionPixelSize(R.styleable.LabelView_android_paddingLeft, (int) (4 * density)),
                a.getDimensionPixelSize(R.styleable.LabelView_android_paddingTop, (int) (4 * density)),
                a.getDimensionPixelSize(R.styleable.LabelView_android_paddingRight, (int) (4 * density)),
                a.getDimensionPixelSize(R.styleable.LabelView_android_paddingBottom, (int) (4 * density))
        );

        a.recycle();
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
        requestLayout();
        invalidate();
    }

    public void setTextSize(int size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTextSize(int unit, float size) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        setRawTextSize(TypedValue.applyDimension(
                unit, size, r.getDisplayMetrics()));
    }

    private void setRawTextSize(float size) {
        if (size != textPaint.getTextSize()) {
            textPaint.setTextSize(size);
            requestLayout();
            invalidate();
        }
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = (int) textPaint.measureText(text) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        ascent = (int) textPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) (-ascent + textPaint.descent()) + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getPaddingLeft(), getPaddingTop() - ascent, textPaint);
    }
}
