package com.diandian.pdd.diandiancha.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.adapter.UserPager1Adapter;
import com.diandian.pdd.diandiancha.baseactivity.BaseFragment;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.util.MyLog;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class Pager2Fragment extends BaseFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.listview)
    ListView listView;
    private UserPager1Adapter adapter;
    @BindView(R.id.kong)
    TextView kong;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager2, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (MyApplication.map == null || MyApplication.map.size() <= 0) {
                    refreshlayout.finishRefresh();
                    return;
                }
                refreshLayout.finishRefresh(10000, false);//传入false表示刷新失败
                adapter.clear();
                initData();
            }
        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(10000);//传入false表示加载失败
//            }
//        });
        initTitle(view, "购物车", "下单");
        setOnRightClick(view, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.map == null || MyApplication.map.size() == 0) {
                    return;
                }
                new AlertDialog.Builder(getActivity()).setTitle("总额" + adapter.getTotal() + "元确认下单？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                MyRequest.addOrder(MyApplication.user.getUserName(),
                                        adapter.getData().get(0).getSellerName(),
                                        new Gson().toJson(adapter.getData()),
                                        adapter.getTotal() + "",
                                        1 + "", new MyRequest.MyCallback() {
                                            @Override
                                            public void sucess(String body) {
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        MyLog.showToast(getActivity(), "下单成功");
                                                        MyApplication.map = null;
                                                        adapter.clear();
                                                    }
                                                });
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
            }
        });
        return view;
    }

    private void initAdapter() {
        adapter = new UserPager1Adapter(getActivity(), true);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    void initData() {
        if (MyApplication.map == null || MyApplication.map.size() == 0) {
            kong.setVisibility(View.VISIBLE);
            return;
        }
        kong.setVisibility(View.GONE);
        //下载MyApplication中的map商品
        for (int id : MyApplication.map.keySet()) {
            MyRequest.queryWares(String.valueOf(id), new MyRequest.MyCallback() {
                @Override
                public void sucess(String body) {
                    refreshLayout.finishRefresh(true);
                    final WaresBean bean = new Gson().fromJson(body, WaresBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addData(bean);
                        }
                    });
                }
            });
        }

    }

}
