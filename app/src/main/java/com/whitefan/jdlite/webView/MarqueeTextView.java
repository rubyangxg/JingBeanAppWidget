package com.whitefan.jdlite.webView;

import android.content.Context;
import android.util.AttributeSet;

//自定义textview使textview一直处于检点状态
public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {
	public MarqueeTextView(Context context) {
		super(context);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}
