package com.diandian.pdd.diandiancha.user;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class Pager1Fragment extends Fragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<WaresBean> list;
    @BindView(R.id.listview)
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager1, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(10000, false);//传入false表示刷新失败
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(10000, false);//传入false表示加载失败
            }
        });
        return view;
    }

    private void initData() {
        MyRequest.queryAllWares("all", new MyRequest.MyCallback() {
            @Override
            public void sucess(String body) {
                list = new Gson().fromJson(body, new TypeToken<ArrayList<WaresBean>>() {
                }.getType());
            }
        });
    }



}
