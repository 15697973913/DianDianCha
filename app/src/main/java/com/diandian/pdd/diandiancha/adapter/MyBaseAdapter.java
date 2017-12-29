package com.diandian.pdd.diandiancha.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    List<T> list = new ArrayList<>();
    LayoutInflater inflater;
    int layout;


    public void initData(List<T> list, boolean clear) {
        if (clear) {
            this.list.clear();
        }
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
            vh = getHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();//
        }
        vh.init(position);
        return convertView;
    }
    abstract ViewHolder getHolder(View view);

   abstract class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }

        abstract void init(int position);
    }
}
