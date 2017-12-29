package com.diandian.pdd.diandiancha.user;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.util.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class Pager2Fragment extends Fragment {
    @BindView(R.id.test)
    TextView test;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.log("fragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        test.setText("1111");
        test.setTextColor(Color.BLACK);
        MyLog.log("fragment", "onCreateView");
        return view;
    }


}
