package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class EyeTestController
{
	private Activity	theActivity		= null;
	private ImageView	imgViewE		= null;
	private ImageView	imgViewUp		= null;
	private ImageView	imgViewDown		= null;
	private ImageView	imgViewLeft		= null;
	private ImageView	imgViewRight	= null;

	public EyeTestController(Activity activity)
	{
		super();
		theActivity = activity;
		initView(activity);
	}

	private void initView(Activity activity)
	{
		imgViewE = (ImageView) activity.findViewById(R.id.imageViewE);
		imgViewUp = (ImageView) activity.findViewById(R.id.imageViewArrowUp);
		imgViewDown = (ImageView) activity.findViewById(R.id.imageViewArrowDown);
		imgViewLeft = (ImageView) activity.findViewById(R.id.imageViewArrowLeft);
		imgViewRight = (ImageView) activity.findViewById(R.id.imageViewArrowRight);

		setTouchEvent(imgViewE);
		setTouchEvent(imgViewUp);
		setTouchEvent(imgViewDown);
		setTouchEvent(imgViewLeft);
		setTouchEvent(imgViewRight);
	}

	private void setTouchEvent(View view)
	{
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
						setColorFilter((ImageView)v, "#CCC7F50E");
					}
					break;
				case MotionEvent.ACTION_UP:
					if (v instanceof ImageView)
					{
						setColorFilter((ImageView)v, Color.TRANSPARENT);
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
