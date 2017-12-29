/**************************************************************************************

 这个是我们自定义的ProgressDialog，在这里我们可以设置我这自己想要的一些属性

 **************************************************************************************/

package com.diandian.pdd.diandiancha.util;



import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandian.pdd.diandiancha.R;

public class CustomProgressDialog extends Dialog {

	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	// 创建一个ProgressDialog，当然参数你也可以传多个，以便设置相关属性
	public static CustomProgressDialog createDialog(Context context,
													String message) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);// 引入样式
		customProgressDialog.setContentView(R.layout.custom_progressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;// 设置在中间显示
		customProgressDialog.setMessage(message);// 设置展示的文字，如：正在加载中
		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog
				.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getBackground();
		animationDrawable.start();
	}

	/**
	 *
	 * setMessage 提示内容，这是我们自定义的dialog布局
	 *
	 */
	public CustomProgressDialog setMessage(String strMessage) {
		TextView tvMsg = (TextView) customProgressDialog
				.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null) {
			tvMsg.setText(strMessage);
		}

		return customProgressDialog;
	}
}
