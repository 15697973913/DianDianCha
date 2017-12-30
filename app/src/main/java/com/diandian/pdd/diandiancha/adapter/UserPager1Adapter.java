package com.diandian.pdd.diandiancha.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.util.MyLog;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserPager1Adapter extends MyBaseAdapter<WaresBean> {


    public UserPager1Adapter(Context context) {
        super(context);
        layout = R.layout.user_pager1_item;
    }
    public UserPager1Adapter(Context context,boolean isdelete) {
        super(context);
        layout = R.layout.user_pager1_item;
        this.isdelete=isdelete;
    }

//    public UserPager1Adapter(Context context,boolean isdelete){
//        this.context=context;
//        inflater= LayoutInflater.from(context);
//        this.isdelete=isdelete;
//    }
    public float getTotal(){
        float total=0;
        for(WaresBean bean:list){
            total+=bean.getWaresPrice()*bean.getNum();
        }
        return total;
    }

    @Override
    ViewHolder getHolder(View view) {
        return new MyViewHolder(view);
    }

    class MyViewHolder extends ViewHolder {
        WaresBean bean;
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_seller)
        TextView itemSeller;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_add)
        ImageView item_add;
        @BindView(R.id.item_less)
        ImageView item_less;
        @BindView(R.id.num)
        TextView num;
        int x = 0;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @Override
        void init(int position) {
            bean = list.get(position);
            Picasso.with(context).load(MyRequest.HOST + "Diandiancha/" + bean.getWaresImgName()).placeholder(R.mipmap.ic_launcher).fit().into(itemImage);
            itemPrice.setText("￥" + bean.getWaresPrice());
            itemSeller.setText(bean.getSellerName());
            itemTitle.setText(bean.getWaresName());
            if (MyApplication.map != null && MyApplication.map.containsKey(bean.getId())) {
                x=MyApplication.map.get(bean.getId());
                bean.setNum(x);
                if(x<=0)return;
                item_less.setVisibility(View.VISIBLE);
                num.setVisibility(View.VISIBLE);
                num.setText(String.valueOf(x));
            }
        }

        @OnClick(R.id.item_add)
        public void onAddClick() {
            x++;
            num.setText(String.valueOf(x));
            num.setVisibility(View.VISIBLE);
            item_less.setVisibility(View.VISIBLE);
            if (MyApplication.map == null) {
                MyApplication.map = new HashMap<>();
            }
            MyApplication.map.put(bean.getId(), x);
            bean.setNum(x);
        }

        @OnClick(R.id.item_less)
        public void onLessClick() {
            x--;
            num.setText(String.valueOf(x));
            MyLog.log("shanchu","MyApplication.map.size()=="+MyApplication.map.size());
            if (x <= 0) {
                Integer ff = MyApplication.map.remove(bean.getId());
                MyLog.log("shanchu",ff+"");
                num.setVisibility(View.GONE);
                item_less.setVisibility(View.GONE);
                if(isdelete){
                    list.remove(bean);
                    notifyDataSetChanged();
                }
            }else {
                MyApplication.map.put(bean.getId(), x);
            }
            bean.setNum(x);
        }


    }

}
