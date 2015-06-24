package com.utils.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.myandroidutils.R;

/**
 * 弹窗类
 * 
 * @author chenrun
 * 
 */
public class AlertDialogs {
	private static Button leftButton;
	private static Button rightButton;
	private static TextView textView; // 提示信息
	public static AlertDialog aDialog;

	public static void dismiss() {

		if (aDialog != null && aDialog.isShowing()) {
			aDialog.dismiss();
		}
	}
	/**
	 * @param context
	 *            显示的Activity
	 * @param title
	 * @param leftText
	 * @param rightText
	 * @param leftOnClickListener
	 *            左边button的监听事件
	 * @param rightOnClickListener
	 *            右边button的监听事件
	 */
	public static void alertDialog(final Context context, String title,
			String leftText, String rightText,
			OnClickListener leftOnClickListener,
			OnClickListener rightOnClickListener) {
		View view = LayoutInflater.from(context).inflate(R.layout.alert, null); // 自定义布局
		textView = (TextView) view.findViewById(R.id.message);
		textView.setText(title);
		leftButton = (Button) view.findViewById(R.id.bt1);
		leftButton.setText(leftText);
		rightButton = (Button) view.findViewById(R.id.bt2);
		rightButton.setText(rightText);
		leftButton.setOnClickListener(leftOnClickListener);
		rightButton.setOnClickListener(rightOnClickListener);
		try {
			if (aDialog != null && aDialog.isShowing()) {
				aDialog.dismiss();
			}
		} catch (Exception e) {
		}
		aDialog = new AlertDialog.Builder(context).create();
		try {
			aDialog.show();
		} catch (Exception e) {
		}
		aDialog.getWindow().setContentView(view); // 把自定義view加上去
		aDialog.setCancelable(false);
	}

	public static void alert(Context context, final String title, String content) { // 一个按钮的弹窗
		if (aDialog != null && aDialog.isShowing()) {
			aDialog.dismiss();
		}
		aDialog = new AlertDialog.Builder(context).create();
		aDialog.setCancelable(false);
		aDialog.setCanceledOnTouchOutside(false);

		final View view = LayoutInflater.from(context).inflate(
				R.layout.alert_d, null); // 自定义布局
		TextView contenTextView = (TextView) view.findViewById(R.id.message);
		Button button = (Button) view.findViewById(R.id.bt1);
		TextView titleText = (TextView) view.findViewById(R.id.title);
		titleText.setText(title);
		contenTextView.setText(content);
		try {
			aDialog.show();
		} catch (Exception e) {
		}
		aDialog.getWindow().setContentView(view); // 把自定義view加上去
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				aDialog.dismiss();
			}
		});
	}
}
