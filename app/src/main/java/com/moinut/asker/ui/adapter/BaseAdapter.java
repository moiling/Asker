package com.moinut.asker.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mDataList = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T data) {
        mDataList.add(data);
        this.notifyItemInserted(mDataList.size() - 1);
    }

    public void addAll(List<T> dataList) {
        mDataList.addAll(dataList);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        mDataList.remove(position);
        this.notifyItemRemoved(position);
    }

    public void remove(T data) {
        int pos = mDataList.indexOf(data);
        mDataList.remove(data);
        this.notifyItemRemoved(pos);
    }

    public void clear() {
        int size = mDataList.size();
        mDataList.clear();
        this.notifyItemRangeChanged(0, size - 1);
    }
}
