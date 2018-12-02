package com.xing.basemvp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 建造者模式实现分割线
 */
public class LinearItemDecoration extends ItemDecoration {

    private static final String TAG = "LinearItemDecoration";
    private int orientation = LinearLayoutManager.VERTICAL;
    private int dividerHeight;
    private int dividerColor;
    private int leftMargin;
    private int rightMargin;
    private Paint paint;

    public static class Builder {
        private int orientation = LinearLayoutManager.VERTICAL;
        private int dividerHeight;
        private int dividerColor;
        private int leftMargin;
        private int rightMargin;
        private DisplayMetrics dm;

        public Builder(Context context) {
            dm = context.getResources().getDisplayMetrics();
        }

        public Builder orientation(int orientation) {
            if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
                throw new IllegalArgumentException("the orientation must be horizontal or vertical");
            }
            this.orientation = orientation;
            return this;
        }

        public Builder dividerHeight(float dividerHeight) {
            this.dividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerHeight, dm);
            return this;
        }

        public Builder dividerColor(int color) {
            this.dividerColor = color;
            return this;
        }

        public Builder margin(int leftMargin, int rightMargin) {
            this.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMargin, dm);
            this.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightMargin, dm);
            return this;
        }

        public LinearItemDecoration build() {
            LinearItemDecoration decoration = new LinearItemDecoration();
            decoration.orientation = orientation;
            decoration.dividerHeight = dividerHeight;
            decoration.dividerColor = dividerColor;
            decoration.leftMargin = leftMargin;
            decoration.rightMargin = rightMargin;
            return decoration;
        }
    }

    private LinearItemDecoration() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, dividerHeight);
        } else {
            //
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        paint.setColor(dividerColor);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(canvas, parent);
        } else {
            drawVerticalDivider(canvas, parent);
        }
    }

    /*
     * 绘制竖直分割线
     */
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {

    }

    /**
     * 绘制水平分割线
     *
     * @param canvas
     */
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        Log.d(TAG, "drawHorizontalDivider: dividerHeight = " + dividerHeight);
        int left = parent.getPaddingLeft() + leftMargin;
        int right = parent.getMeasuredWidth() - parent.getPaddingRight() - rightMargin;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int top = childView.getBottom();
            int bottom = top + dividerHeight;
            canvas.drawRect(left, top, right < 0 ? 0 : right, bottom, paint);
        }
    }
}
