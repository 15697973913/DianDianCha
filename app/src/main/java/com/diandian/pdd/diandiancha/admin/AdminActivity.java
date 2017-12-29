package com.diandian.pdd.diandiancha.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.baseactivity.BaseActivity;
import com.diandian.pdd.diandiancha.baseactivity.MyApplication;
import com.diandian.pdd.diandiancha.bean.User;
import com.diandian.pdd.diandiancha.request.MyRequest;
import com.diandian.pdd.diandiancha.util.MyLog;
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
import butterknife.OnClick;

public class AdminActivity extends BaseActivity {

    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ArrayList<User> list;
    MyAdapter adapter;
    int pager = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        initTitle(false, "管理员", "退出帐号");
        setOnRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.user=null;
                getSharedPreferences("user",MODE_PRIVATE).edit().clear().apply();
                finish();
            }
        });

        adapter=new MyAdapter();
        listview.setAdapter(adapter);
        initData();


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshLayout.finishRefresh(5000,false);//传入false表示刷新失败
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000,false);//传入false表示加载失败
            }
        });
    }



    private void initData() {
        MyRequest.queryAll(new MyRequest.MyCallback() {
            @Override
            public void sucess(String body) {
                list = new Gson().fromJson(body, new TypeToken<List<User>>() {
                }.getType());
                for (User my:list) {
                    if(my.getId()==MyApplication.user.getId()){
                        list.remove(my);
                        break;
                    }
                }
                MyLog.log("netLog",list.size()+"");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.init(list);
                    }
                });
                refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
    }

    class MyAdapter extends BaseAdapter {
ArrayList<User> list=new ArrayList();
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public User getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;

            if (convertView == null) {
                convertView = LayoutInflater.from(AdminActivity.this).inflate(R.layout.item_user, parent, false);

                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.init(position);
            return convertView;
        }

        public void init(ArrayList<User> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();

        }

        class ViewHolder {
            @BindView(R.id.text_userName)
            TextView textUserName;
            @BindView(R.id.type)
            TextView type;
            @BindView(R.id.text)
            TextView text;
            @BindView(R.id.text_phone)
            TextView text_phone;
            int position;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);

            }

            public void init(int position) {
                this.position=position;
                User user = list.get(position);
                textUserName.setText("用户名："+user.getUserName());
                String typeString = "";
                switch (user.getUserType()){
                    case 1:
                        typeString="管理员帐号";
                        break;
                    case 2:
                        typeString="商户帐号";
                        break;
                    case 3:
                        typeString="用户帐号";
                        break;
                }
                type.setText(typeString);
                text.setText(user.getUserExplain());
                text_phone.setText(user.getPhoneNum());

            }
            @OnClick(R.id.item_delete)
            public void onItemDelete(){
                MyLog.log("netLog",position+"删除");
                new AlertDialog.Builder(AdminActivity.this).setTitle("确认删除吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                MyRequest.deleteUser(list.get(position).getId(), new MyRequest.MyCallback() {
                                    @Override
                                    public void sucess(String body) {
                                        list.remove(position);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                notifyDataSetChanged();
                                            }
                                        });
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
        }


    }

    long time;

    @Override
    public void onBackPressed() {
        long t=System.currentTimeMillis();
        if (t - time < 1500) {
            finishAll();
        } else {
            MyLog.showToast(this, "点两次退出");
            time = t;
        }
    }
}
