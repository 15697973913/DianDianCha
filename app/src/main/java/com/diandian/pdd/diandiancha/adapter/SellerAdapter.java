package com.diandian.pdd.diandiancha.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.diandian.pdd.diandiancha.sellerview.SellerActivity;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.util.CustomProgressDialog;
import com.diandian.pdd.diandiancha.util.MyLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/12/28 0028.
 * 商家商品信息Adapter
 */

public class SellerAdapter extends CommenAdapter {
    public Context context;

    public SellerAdapter(Context context) {
        super(MyApplication.sellerWaresList);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, R.layout.waresitem_layout, convertView, parent);
        ImageView img = viewHolder.getView(R.id.waresitem_iv_img);
        TextView name = viewHolder.getView(R.id.waresitem_tv_name);
        TextView price = viewHolder.getView(R.id.waresitem_tv_price);
        ImageView delete = viewHolder.getView(R.id.waresitem_but_delete);
        name.setText(MyApplication.sellerWaresList.get(position).getWaresName());
        price.setText("¥" + MyApplication.sellerWaresList.get(position).getWaresPrice());
        Picasso.with(context).load(MyRequest.ImageUrl + MyApplication.sellerWaresList.get(position).getWaresImgName()).placeholder(R.drawable.default_img).into(img);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context).setTitle("确认删除吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context, "正在删除...");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();
                                MyRequest.deleteWares(MyApplication.sellerWaresList.get(position).getId() + "", new MyRequest.MyCallback() {
                                    public void onFailure(Call call, IOException e) {
                                        super.onFailure(call, e);
                                        progressDialog.dismiss();
                                        handler.sendEmptyMessage(0x1113);
                                    }


                                    public void failed(String msg) {
                                        super.failed(msg);
                                        progressDialog.dismiss();
                                        handler.sendEmptyMessage(0x1113);
                                    }

                                    public void sucess(String body) {
                                        if (body.equals("成功")) {
                                            MyRequest.queryAllWares(MyApplication.user.getUserName(), new MyRequest.MyCallback() {
                                                public void sucess(String body) {
                                                    MyApplication.sellerWaresList = new Gson().fromJson(body, new TypeToken<ArrayList<WaresBean>>() {
                                                    }.getType());
                                                    handler.sendEmptyMessage(0x1112);
                                                    progressDialog.dismiss();
                                                }
                                            });
                                        } else {
                                            progressDialog.dismiss();
                                            handler.sendEmptyMessage(0x1113);
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();

            }
        });

        return viewHolder.getmConverView();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x1112:
                    SellerActivity.activity.updateAdapter();
                    MyLog.showToast1(context,"删除成功");
                    break;
                case 0x1113:
                    SellerActivity.activity.updateAdapter();
                    MyLog.showToast1(context,"删除失败");
                    break;
            }
        }
    };
}
