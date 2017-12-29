package com.diandian.pdd.diandiancha.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public abstract class CommenAdapter<T> extends BaseAdapter {
    List<T> mData = new ArrayList<>();

    public CommenAdapter(List<T> mData) {
        this.mData = mData;
    }

    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
