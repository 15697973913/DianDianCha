package com.diandian.pdd.diandiancha.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diandian.pdd.diandiancha.R;


public class MyLog {
	private static boolean debug=true;
	public static void log(String msg){
		if(msg==null){
			return;
		}
		if(debug){
		Log.d("TAG", msg);
		}
	}
	public static void log(String TAG,String msg){
		if(msg==null){
			return;
		}
		if(debug){
			Log.d(TAG, msg);
		}
	}
	public static void error(String msg){
		if(msg==null){
			return;
		}
		if(debug){
			Log.d("error", msg);
		}
	}
    private static Toast toast;
    private static TextView tv;
    public static   void showToast(Context context, String text) {
        if (toast == null) {
			View v = LayoutInflater.from(context).inflate(R.layout.toast, null);
			tv =  v.findViewById(R.id.text);
			toast = new Toast(context);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.setView(v);
		}
            tv.setText(text);

        toast.show();
    }
}
