package com.diandian.pdd.diandiancha.adapter;

import android.view.View;
import android.widget.ImageView;

import com.diandian.pdd.diandiancha.R;
import com.diandian.pdd.diandiancha.bean.WaresBean;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class UserPager1Adapter extends MyBaseAdapter<WaresBean>{
    @Override
    ViewHolder getHolder(View view) {
        layout= R.layout.user_pager1_item;
        return new ViewHolder(view) {
            @BindView(R.id.item_image)
            ImageView item_image;
            @Override
            void init(int position) {


            }
        };
    }
}
