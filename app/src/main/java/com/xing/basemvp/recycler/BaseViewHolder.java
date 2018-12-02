package com.xing.basemvp.recycler;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.LinkedHashSet;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View itemView;                                     // item view 根布局
    private SparseArray<View> views;                           // itemView 中的子 view
    private LinkedHashSet<Integer> childClickViewIds;          // itemView 中设置点击的子 view ids
    private LinkedHashSet<Integer> childLongClickViewIds;      // itemView 中设置长按的子 view ids
    private BaseRecyclerAdapter adapter;                       // recyclerview base adapter

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
        childClickViewIds = new LinkedHashSet<>();
        childLongClickViewIds = new LinkedHashSet<>();
    }

    protected void setBaseRecyclerAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public View getItemView() {
        return itemView;
    }

    public <V extends View> V getView(@IdRes int resId) {
        View view = views.get(resId);
        if (view == null) {
            view = itemView.findViewById(resId);
            views.put(resId, view);
        }
        return (V) view;
    }

    public BaseViewHolder setText(@IdRes int resId, CharSequence charSequence) {
        TextView textView = getView(resId);
        if (textView != null) {
            textView.setText(charSequence);
        }
        return this;
    }

    public BaseViewHolder setText(@IdRes int resId, @StringRes int strId) {
        TextView textView = getView(resId);
        if (textView != null) {
            textView.setText(strId);
        }
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageResource(imageResId);
        }
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public BaseViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundRes);
        }
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTextColor(textColor);
        }
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageDrawable(drawable);
        }
        return this;
    }


    public BaseViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        if (view != null) {
            view.setImageBitmap(bitmap);
        }
        return this;
    }

    public BaseViewHolder setAlpha(@IdRes int viewId, float value) {
        View view = getView(viewId);
        if (view != null) {
            view.setAlpha(value);
        }
        return this;
    }

    public BaseViewHolder setVisibility(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public BaseViewHolder setRecycler(@IdRes int viewId, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter) {
        RecyclerView view = getView(viewId);
        if (view != null) {
            view.setLayoutManager(layoutManager);
            view.setAdapter(adapter);
        }
        return this;
    }


    public BaseViewHolder setTypeface(@IdRes int viewId, Typeface typeface) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            if (view != null) {
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
        }
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        if (view != null) {
            view.setProgress(progress);
        }
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        if (view != null) {
            view.setMax(max);
            view.setProgress(progress);
        }
        return this;
    }

    public BaseViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        if (view != null) {
            view.setMax(max);
        }
        return this;
    }

    public BaseViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        if (view != null) {
            view.setRating(rating);
        }
        return this;
    }

    public BaseViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        if (view != null) {
            view.setMax(max);
            view.setRating(rating);
        }
        return this;
    }

    /**
     * 为指定的子 view 设置点击回调
     *
     * @param viewIds 所有可点击回调的子 view 数组
     * @return
     */
    public BaseViewHolder setChildViewsClickListener(@IdRes int... viewIds) {
        if (viewIds != null && viewIds.length > 0) {
            for (int viewId : viewIds) {
                childClickViewIds.add(viewId);
                final View view = getView(viewId);
                if (view != null) {
                    if (!view.isClickable()) {
                        view.setClickable(true);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (adapter.getOnChildViewClickListener() != null) {
                                adapter.getOnChildViewClickListener().onChildViewClick(adapter, v, getClickPosition());
                            }
                        }
                    });
                }
            }
        }
        return this;
    }

    /**
     * 为指定的子 view 设置长按回调
     *
     * @param viewIds 所有可长按回调的子 view 数组
     * @return
     */
    public BaseViewHolder setChildViewsLongClickListener(@IdRes int... viewIds) {
        if (viewIds != null && viewIds.length > 0) {
            for (int viewId : viewIds) {
                childLongClickViewIds.add(viewId);
                final View view = getView(viewId);
                if (view != null) {
                    if (!view.isLongClickable()) {
                        view.setLongClickable(true);
                    }
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return adapter.getOnChildViewLongClickListener() != null &&
                                    adapter.getOnChildViewLongClickListener().onChildViewLongClick(adapter, v, getClickPosition());
                        }
                    });
                }
            }
        }
        return this;
    }

    private int getClickPosition() {
        if (getLayoutPosition() >= adapter.getHeaderViewCount()) {
            return getLayoutPosition() - adapter.getHeaderViewCount();
        }
        return 0;
    }

    /**
     * Sets the listview or gridview's item click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item on click listener;
     * @return The BaseViewHolder for chaining.
     * Please use {@link #addOnClickListener(int)} (int)} (adapter.setOnItemChildClickListener(listener))}
     */
    @Deprecated
    public BaseViewHolder setOnItemClickListener(@IdRes int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    /**
     * Sets the listview or gridview's item long click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item long click listener;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnItemLongClickListener(@IdRes int viewId, AdapterView.OnItemLongClickListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    /**
     * Sets the listview or gridview's item selected click listener of the view
     *
     * @param viewId   The view id.
     * @param listener The item selected click listener;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnItemSelectedClickListener(@IdRes int viewId, AdapterView.OnItemSelectedListener listener) {
        AdapterView view = getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    /**
     * Sets the on checked change listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The checked change listener of compound button.
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setOnCheckedChangeListener(@IdRes int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    /**
     * Sets the tag of the view.
     *
     * @param viewId The view id.
     * @param key    The key of tag;
     * @param tag    The tag;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * Sets the checked status of a checkable.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseViewHolder for chaining.
     */
    public BaseViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        // View unable cast to Checkable
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public BaseViewHolder setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        if (view != null) {
            view.setAdapter(adapter);
        }
        return this;
    }
}
