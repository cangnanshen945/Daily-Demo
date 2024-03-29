package com.example.gradientdemo;

/**
 * author: zhangjunqian
 * created on: 2023/5/14 12:19
 * description:
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 使用圆角时，应设置圆角相同的background
 */

public class ShadowRelativeLayout extends RelativeLayout {
    /**
     * 阴影的颜色
     */
    private int shadowColor = Color.argb(90, 30, 0, 0);
    /**
     * 高斯模糊的模糊半径,值越大越模糊，越小越清晰,
     */
    private float shadowBlur = 200;
    /**
     * 阴影的圆角
     */
    private float shadowRadius = 0;

    /**
     * 阴影的偏移
     */
    private float shadowDx = 0;
    private float shadowDy = 0;

    public ShadowRelativeLayout(@NonNull Context context) {
        this(context, null);
    }

    public ShadowRelativeLayout(@NonNull Context context,
                                @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dealAttrs(context, attrs);
        setPaint();
    }

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void draw(Canvas canvas) {
        setInsetBackground();
        canvas.drawRoundRect(getRectF(), shadowRadius, shadowRadius, mPaint);
        super.draw(canvas);
    }

    private boolean setInsetBackground() {
        Drawable background = getBackground();
        if (background == null || background instanceof InsetDrawable) {
            return false;
        }
        InsetDrawable drawable =
                new InsetDrawable(background, getPaddingLeft(), getPaddingTop(),
                        getPaddingRight(), getPaddingBottom());
        setBackground(drawable);
        return true;
    }

    private RectF getRectF() {
        return new RectF(getPaddingLeft() + shadowDx, getPaddingTop() + shadowDy,
                getWidth() - getPaddingRight() + shadowDx,
                getHeight() - getPaddingBottom() + shadowDy);
    }

    private void dealAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowRelativeLayout);
        if (typedArray != null) {
            shadowColor = typedArray.getColor(R.styleable.ShadowRelativeLayout_shadow_color, shadowColor);
            shadowRadius =
                    typedArray.getDimension(R.styleable.ShadowRelativeLayout_shadow_radius, shadowRadius);
            shadowBlur =
                    typedArray.getDimension(R.styleable.ShadowRelativeLayout_shadow_blur, shadowBlur);
            shadowDx = typedArray.getDimension(R.styleable.ShadowRelativeLayout_shadow_dx, shadowDx);
            shadowDy = typedArray.getDimension(R.styleable.ShadowRelativeLayout_shadow_dy, shadowDy);
            typedArray.recycle();
        }
    }

    private void setPaint() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);  // 关闭硬件加速,阴影才会绘制
        // todo 从AttributeSet获取设置的值
        mPaint.setAntiAlias(true);
        mPaint.setColor(shadowColor);
        mPaint.setMaskFilter(new BlurMaskFilter(shadowBlur, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    public boolean isOpaque() { //纯色或图片做背景时,draw之前会有黑底色，
        return false;
    }
}
