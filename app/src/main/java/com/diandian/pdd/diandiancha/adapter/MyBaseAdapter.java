package com.diandian.pdd.diandiancha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.diandian.pdd.diandiancha.bean.WaresBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    boolean isdelete;
    Context context;
    List<T> list = new ArrayList<>();
    LayoutInflater inflater;
    int layout;

    public MyBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public MyBaseAdapter(Context context, boolean isdelete) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.isdelete = isdelete;
    }

    public List<T> getData() {
        return list;
    }

    public void addAll(List<T> list, boolean clear) {
        if (clear) {
            this.list.clear();
        }
        if (list != null) {
            this.list.addAll(list);
        }

        notifyDataSetChanged();
    }

    public void addData(T bean) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(bean);
        notifyDataSetChanged();
    }

    public void clear() {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();

        notifyDataSetChanged();
    }

//    @Override
//    public void notifyDataSetChanged() {
//        ((Activity) context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//               run();
//            }
//        });
//    }
    void run(){
        super.notifyDataSetChanged();
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
        } else {
            vh = (ViewHolder) convertView.getTag();//
        }
        vh.init(position);
        return convertView;
    }

    abstract ViewHolder getHolder(View view);


    abstract class ViewHolder {
        ViewHolder(View view) {
        }

        abstract void init(int position);
    }
}
