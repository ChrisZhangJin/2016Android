package com.example.customviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ColorOptionView extends LinearLayout {

	private TextView text;
	private ImageView image;

	public ColorOptionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorOptionsView, 0, 0);

		String title = array.getString(R.styleable.ColorOptionsView_titleText);
		int color = array.getColor(R.styleable.ColorOptionsView_valueColor, android.R.color.black);
		array.recycle();
		
		View view = inflate(context, R.layout.color_option_view, null);
		text = (TextView) view.findViewById(R.id.textView);
		text.setText(title);
		image = (ImageView) view.findViewById(R.id.imageView);
		image.setBackgroundColor(color);
		
		addView(view);
	}

	public void setImageVisible(boolean visible) {
		image.setVisibility(visible ? VISIBLE : GONE);
	}

	public void setImageColor(int color) {
		image.setBackgroundColor(color);
	}

}
