package com.diandian.pdd.diandiancha.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class ViewHolder {
    private View mConverView;

    //1、SparseArray效率较高
    //2、key值只能是integer
    SparseArray<View> mViews=null;

    public ViewHolder(Context context, int layoutID, ViewGroup parent) {
        mViews=new SparseArray<>();
        mConverView= LayoutInflater.from(context).inflate(layoutID,parent,false);
        mConverView.setTag(this);
    }

    /**
     * 获取一个ViewHodler的对象
     *
     * @param context    上下文
     * @param layoutId   item布局ID
     * @param converView listView一个item的View
     * @param parent     父容器
     * @return iewHodler的对象
     */
    public static ViewHolder get(Context context,int layoutId,View converView, ViewGroup parent){
        if (converView==null){
            return new ViewHolder(context,layoutId,parent);
        }
        return (ViewHolder) converView.getTag();
    }
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=mConverView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getmConverView(){
        return mConverView;
    }

}
