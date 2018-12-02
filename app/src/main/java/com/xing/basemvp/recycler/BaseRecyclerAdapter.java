package com.xing.basemvp.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private final int BASE_TYPE_HEADER = 0x1000;
    private final int BASE_TYPE_FOOTER = 0x2000;
    protected List<T> dataList;    // 数据集
    protected SparseArray<View> headerViews = new SparseArray<>();
    protected SparseArray<View> footerViews = new SparseArray<>();
    protected Context mContext;
    protected int[] layoutResIds;

    protected BaseRecyclerAdapter(List<T> list, @IdRes int... layoutResId) {
        this.dataList = list;
        layoutResIds = layoutResId;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("debugdebug", "onCreateViewHolder: ============");
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        BaseViewHolder baseViewHolder;
        if (headerViews.get(viewType) != null) {
            baseViewHolder = new BaseViewHolder(headerViews.get(viewType));
        } else if (headerViews.get(viewType) != null) {
            baseViewHolder = new BaseViewHolder(footerViews.get(viewType));
        } else {
            /**
             * 注意这里的 layoutId 是从 layoutResIds 数组中,用 viewType 作为下标获取到的，所以 getItemType() 方法
             * 返回值应该在 layoutResIds 数组长度范围内，否则将出现数组越界异常
             */
            View itemView = inflater.inflate(layoutResIds[viewType], parent, false);
            baseViewHolder = new BaseViewHolder(itemView);
        }
        baseViewHolder.setBaseRecyclerAdapter(this);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        if (isHeaderPosition(position)) {
            return;
        } else if (isFooterPosition(position)) {
            return;
        }
        bindListener(baseViewHolder, position - getHeaderViewCount());
        T data = dataList.get(position - getHeaderViewCount());
        // 子类实现具体绑定
        bind(this, baseViewHolder, data, position - getHeaderViewCount());
    }

    private void bindListener(BaseViewHolder baseViewHolder, final int position) {
        View itemView = baseViewHolder.getItemView();
        if (itemView != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(BaseRecyclerAdapter.this, v, position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(BaseRecyclerAdapter.this, v, position);
                    }
                    return true;
                }
            });
        }
    }

    protected abstract void bind(BaseRecyclerAdapter<T> adapter, BaseViewHolder baseViewHolder, T data, int position);

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderViewCount()) {
            return headerViews.keyAt(position);  // BASE_TYPE_HEADER + getHeaderViewCount()
        } else if (position >= getHeaderViewCount() + getRealItemCount()) {
            return footerViews.keyAt(position);
        } else {
            return super.getItemViewType(position);
        }
    }

    public void addItem(@IntRange(from = 0) int position, @NonNull T data) {
        dataList.add(position, data);
        int internalPosition = position + getHeaderViewCount();
//        notifyItemInserted(internalPosition);
        notifyItemRangeChanged(internalPosition, dataList.size() - internalPosition);
    }

    public void removeItem(@IntRange(from = 0) int position) {
        dataList.remove(position);
        int internalPosition = position + getHeaderViewCount();
//        notifyItemRemoved(internalPosition);
        Log.d("debugdebug", "removeItem: internalPosition = " + internalPosition + ", ==== " + (dataList.size() - internalPosition));
        notifyItemRangeChanged(internalPosition, dataList.size() - internalPosition);
    }


    @Override
    public int getItemCount() {
        return getRealItemCount() + getHeaderViewCount() + getFooterViewCount();
    }

    protected int getRealItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 添加 header
     *
     * @param headerView
     */
    public void addHeaderView(View headerView) {
        headerViews.put(BASE_TYPE_HEADER + getHeaderViewCount(), headerView);
    }

    /**
     * 添加 footer
     *
     * @param footerView
     */
    public void addFooterView(View footerView) {
        footerViews.put(BASE_TYPE_FOOTER + getRealItemCount() + getFooterViewCount(), footerView);
    }

    /**
     * 获取 header 个数
     *
     * @return
     */
    protected int getHeaderViewCount() {
        return headerViews.size();
    }

    /**
     * 获取 footer 个数
     *
     * @return
     */
    protected int getFooterViewCount() {
        return footerViews.size();
    }

    /**
     * 当前位置是否是 header
     *
     * @param position
     * @return
     */
    protected boolean isHeaderPosition(int position) {
        return position < getHeaderViewCount();
    }

    /**
     * 当前位置是否是 footer
     *
     * @param position
     * @return
     */
    protected boolean isFooterPosition(int position) {
        return position >= getHeaderViewCount() + getRealItemCount();
    }

    protected View getHeaderViewByPosition(int position) {
        return headerViews.get(BASE_TYPE_HEADER + position);
    }

    protected View getFooterViewByPosition(int position) {
        return footerViews.get(BASE_TYPE_FOOTER + position);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    public interface OnChildViewClickListener {
        void onChildViewClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    public interface OnChildViewLongClickListener {
        boolean onChildViewLongClick(BaseRecyclerAdapter adapter, View view, int position);
    }

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnChildViewClickListener onChildViewClickListener;
    private OnChildViewLongClickListener onChildViewLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    public void setOnChildViewLongClickListener(OnChildViewLongClickListener onChildViewLongClickListener) {
        this.onChildViewLongClickListener = onChildViewLongClickListener;
    }

    public OnChildViewClickListener getOnChildViewClickListener() {
        return onChildViewClickListener;
    }

    public OnChildViewLongClickListener getOnChildViewLongClickListener() {
        return onChildViewLongClickListener;
    }
}
