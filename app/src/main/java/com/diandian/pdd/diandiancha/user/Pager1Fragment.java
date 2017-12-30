package com.diandian.pdd.diandiancha.user;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.adapter.UserPager1Adapter;
import com.diandian.pdd.diandiancha.baseactivity.BaseFragment;
import com.diandian.pdd.diandiancha.bean.WaresBean;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class Pager1Fragment extends BaseFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<WaresBean> list;
    @BindView(R.id.listview)
    ListView listView;
    private UserPager1Adapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager1, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.finishRefresh(10000);//传入false表示刷新失败
                initData();
            }
        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(10000);//传入false表示加载失败
//            }
//        });
        initTitle(view,"商品","");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initAdapter() {
        adapter=new UserPager1Adapter(getActivity());
        listView.setAdapter(adapter);
    }

    private void initData() {
        MyRequest.queryAllWares("all", new MyRequest.MyCallback() {
            @Override
            public void sucess(String body) {
                refreshLayout.finishRefresh(true);
                list = new Gson().fromJson(body, new TypeToken<ArrayList<WaresBean>>() {
                }.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(list,true);
                    }
                });
            }

            @Override
            public void failed(String msg) {
                super.failed(msg);
                refreshLayout.finishLoadmore(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                refreshLayout.finishLoadmore(false);
            }
        });
    }



}
