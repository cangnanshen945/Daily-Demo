package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class HorizontalAppBarBehavior extends CoordinatorLayout.Behavior<View> {

    private int lastDx;

    public HorizontalAppBarBehavior() {
        super();
    }

    public HorizontalAppBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, View child, View directTargetChild, View target, int axes, int type) {
        // 支持垂直方向上的滚动事件
        return axes == View.SCROLL_AXIS_HORIZONTAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed, int type) {
        // 当向左滑动时，优先滑动左侧的 child 视图
        if (dx > 0) {
            int remaining = dx;
            if (child.getRight() > 0) {
                int scrollBy = Math.min(remaining, child.getRight());
                child.offsetLeftAndRight(-scrollBy);
                consumed[0] += scrollBy;
                remaining -= scrollBy;
            }
            lastDx = remaining;
        } else {
            lastDx = dx;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        // 当向右滑动时，优先滑动右侧的 target（RecyclerView）视图
        if (lastDx < 0 && dxUnconsumed == 0 && child.getLeft() < 0) {
            int scrollBy = Math.min(-lastDx, -child.getLeft());
            child.offsetLeftAndRight(scrollBy);
        }
    }
}
