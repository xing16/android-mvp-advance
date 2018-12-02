package com.xing.basemvp.recycler;

import java.util.List;

public abstract class MultiTypeRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    public MultiTypeRecyclerAdapter(List<T> list, int... layoutResId) {
        super(list, layoutResId);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return headerViews.keyAt(position);
        } else if (isFooterPosition(position)) {
            return footerViews.keyAt(position - getHeaderViewCount() - getRealItemCount());
        } else {
            return getItemType(position - getHeaderViewCount());
        }
    }

    /**
     * 因为在 onCreateViewHolder()中，layoutId 是从 layoutResIds 数组中,用 viewType 作为下标获取到的，
     * 所以 getItemType() 方法返回值应该在 layoutResIds 数组长度范围内，否则将出现数组越界异常
     */
    protected abstract int getItemType(int position);
}
