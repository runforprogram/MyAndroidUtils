package com.utils.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView; 
/**
 * @author run 
 * 使videoview可以设置自定义的大小
 *
 */
public class MyVideoView extends VideoView {
	private int mVideoWidth;
	private int mVideoHeight;
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("@@@@", "onMeasure");

      //  setMeasuredDimension(mForceWidth, mForceHeight);
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
