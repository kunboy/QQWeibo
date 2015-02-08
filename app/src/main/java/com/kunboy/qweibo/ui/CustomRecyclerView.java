package com.kunboy.qweibo.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomRecyclerView extends RecyclerView {

	public CustomRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {

		return super.onTouchEvent(arg0);
	}

	@Override
	public boolean canScrollVertically(int direction) {
		return super.canScrollVertically(direction);
	}
}
