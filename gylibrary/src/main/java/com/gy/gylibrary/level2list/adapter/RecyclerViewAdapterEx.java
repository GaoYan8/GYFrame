/**
 * RecyclerViewAdapterEx
 * https://github.com/hgDendi/ExpandableRecyclerView
 * <p>
 * Copyright (c) 2017 hg.dendi
 * <p>
 * MIT License
 * https://rem.mit-license.org/
 * <p>
 * email: hg.dendi@gmail.com
 * Date: 2017-10-18
 */

package com.gy.gylibrary.level2list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.gy.gylibrary.level2list.ViewProducer;

import java.util.List;

public abstract class RecyclerViewAdapterEx<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = ViewProducer.VIEW_TYPE_EMPTY;
    private static final int TYPE_HEADER = ViewProducer.VIEW_TYPE_HEADER;

    protected ViewProducer mEmptyViewProducer;
    protected ViewProducer mHeaderViewProducer;
    protected boolean mIsEmpty;

    /**
     * equivalent to {@link #getItemCount()}
     *
     * @return
     */
    public abstract int getCount();

    /**
     * equivalent to {@link #getItemViewType(int)}
     *
     * @param positoin
     * @return
     */
    public abstract int getViewType(int positoin);

    /**
     * equivalent to {@link #onCreateViewHolder(ViewGroup, int)}}
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract VH onCreateCustomizeViewHolder(final ViewGroup parent, int viewType);

    /**
     * equivalent to onBindViewHolder(RecyclerView.ViewHolder, int)
     *
     * @param holder
     * @param position
     */
    public abstract void onBindCustomizeViewHolder(final VH holder, int position);

    protected void onBindCustomizeViewHolder(final VH holder, int position, List<Object> payloads) {
        onBindCustomizeViewHolder(holder, position);
    }

    /**
     * set empty view
     *
     * @param emptyViewProducer see {@link ViewProducer}
     */
    public void setEmptyViewProducer(ViewProducer emptyViewProducer) {
        if (mEmptyViewProducer != emptyViewProducer) {
            mEmptyViewProducer = emptyViewProducer;
            if (mIsEmpty) {
                notifyDataSetChanged();
            }
        }
    }

    /**
     * set Header view
     *
     * @param emptyViewProducer see {@link ViewProducer}
     */
    public void setHeaderViewProducer(ViewProducer emptyViewProducer) {
        if (mHeaderViewProducer != emptyViewProducer) {
            mHeaderViewProducer = emptyViewProducer;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderViewProducer != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mIsEmpty) {
            return TYPE_EMPTY;
        }
        final int viewType = getViewType(position);
        if (viewType != TYPE_EMPTY) {
            return viewType;
        } else {
            throw new IllegalStateException("getViewType conflicts with original TYPE_EMPTY : " + viewType);
        }
    }

    @Override
    public final int getItemCount() {
        int result = getCount();
        if (result == 0 && mEmptyViewProducer != null) {
            mIsEmpty = true;
            return mHeaderViewProducer == null ? 1 : 2;
        }
        if (mHeaderViewProducer != null) {
            result++;
        }
        mIsEmpty = false;
        return result;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return mEmptyViewProducer.onCreateViewHolder(parent);
        } else if (viewType == TYPE_HEADER) {
            return mHeaderViewProducer.onCreateViewHolder(parent);
        } else {
            return onCreateCustomizeViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_EMPTY) {
            mEmptyViewProducer.onBindViewHolder(holder);
        } else if (holder.getItemViewType() == TYPE_HEADER) {
            mHeaderViewProducer.onBindViewHolder(holder);
        } else {
            if (mHeaderViewProducer != null) {
                position--;
            }
            onBindCustomizeViewHolder((VH) holder, position);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (holder.getItemViewType() == TYPE_EMPTY) {
            mEmptyViewProducer.onBindViewHolder(holder);
        } else if (holder.getItemViewType() == TYPE_HEADER) {
            mHeaderViewProducer.onBindViewHolder(holder);
        } else {
            if (mHeaderViewProducer != null) {
                position--;
            }
            onBindCustomizeViewHolder((VH) holder, position, payloads);
        }
    }
}

