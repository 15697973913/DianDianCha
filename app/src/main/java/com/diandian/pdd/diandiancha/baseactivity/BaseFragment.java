package com.diandian.pdd.diandiancha.baseactivity;

import android.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class BaseFragment extends Fragment {


    public void initTitle(View view,String title,String rightText) {
        TextView tt = view.findViewById(R.id.title_center);
        tt.setVisibility(View.VISIBLE);
        tt.setText(title);
        if(!TextUtils.isEmpty(rightText)){
            ((TextView)view.findViewById(R.id.title_right)).setText(rightText);
        }
    }
    public void setOnRightClick(View view,View.OnClickListener listener){
        view.findViewById(R.id.title_right).setOnClickListener(listener);
    }
}
