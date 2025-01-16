package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class HorizontalRecyclerViewBehavior extends CoordinatorLayout.Behavior<View> {

    public HorizontalRecyclerViewBehavior() {
        super();
    }

    public HorizontalRecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, View child, View directTargetChild, View target, int axes, int type) {
        // 支持水平方向上的滚动事件
        return axes == ViewCompat.SCROLL_AXIS_HORIZONTAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed, int type) {
        // 当向右滑动时，优先滚动右侧的 child（RecyclerView）视图
        if (dx < 0) {
            int initialScrollX = child.getScrollX();
            child.scrollBy(dx, 0);
            consumed[0] = child.getScrollX() - initialScrollX;
        }
    }
}
