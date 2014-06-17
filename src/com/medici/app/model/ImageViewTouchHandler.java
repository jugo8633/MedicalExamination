package com.medici.app.model;

import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageViewTouchHandler
{

	public ImageViewTouchHandler()
	{
		super();
	}

	public void setTouchEvent(View view, final Handler handler)
	{
		if (null == view)
		{
			return;
		}
		view.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					if (v instanceof ImageView)
					{
						setColorFilter((ImageView) v, "#CCC7F50E");
					}
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					if (v instanceof ImageView)
					{
						setColorFilter((ImageView) v, Color.TRANSPARENT);
						EventHandler.notify(handler, EventMessage.MSG_SELECTED, v.getId(), 0, null);
					}
					break;
				}
				return true;
			}
		});
	}

	private void setColorFilter(ImageView imageView, String strColor)
	{
		imageView.setColorFilter(Color.parseColor(strColor));
	}

	private void setColorFilter(ImageView imageView, int nColor)
	{
		imageView.setColorFilter(nColor);
	}
}
