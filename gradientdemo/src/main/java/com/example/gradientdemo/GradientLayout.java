package com.example.gradientdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * author: zhangjunqian
 * created on: 2023/5/14 11:05
 * description:
 */
public class GradientLayout extends RelativeLayout {
    private float startX = 0f;
    private float startY = 0f;
    private float endX = 1f;
    private float endY = 1f;

    private int startColor = Color.parseColor("#4F4F62");

    private int endColor = Color.parseColor("#3C5773");
    private Paint mPaint;
    private LinearGradient gradient;

    public GradientLayout(Context context) {
        this(context, null);
    }

    public GradientLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        setWillNotDraw(false);
    }

    private void init(AttributeSet attrs) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GradientLayout);
            if (typedArray != null) {
                startX = typedArray.getFloat(R.styleable.GradientLayout_startX, 0f);
                startY = typedArray.getFloat(R.styleable.GradientLayout_startY, 0f);
                endX = typedArray.getFloat(R.styleable.GradientLayout_endX, 1f);
                endY = typedArray.getFloat(R.styleable.GradientLayout_endY, 1f);
                startColor = typedArray.getColor(R.styleable.GradientLayout_startColor, Color.parseColor("#4F4F62"));
                endColor = typedArray.getColor(R.styleable.GradientLayout_endColor, Color.parseColor("#3C5773"));
                typedArray.recycle();
            }
            mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (gradient == null) {
            gradient = new LinearGradient(width * startX, height * startY,
                    width * endX, height * endY, new int[]{startColor, endColor}, null, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient);
            mPaint.setMaskFilter(new BlurMaskFilter(200, BlurMaskFilter.Blur.SOLID));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }
}
