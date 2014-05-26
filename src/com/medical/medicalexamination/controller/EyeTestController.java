package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Device;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EyeTestController
{
	private Activity		theActivity		= null;
	private ImageView		imgViewE		= null;
	private ImageView		imgViewUp		= null;
	private ImageView		imgViewDown		= null;
	private ImageView		imgViewLeft		= null;
	private ImageView		imgViewRight	= null;
	private ImageView		imgClose		= null;
	private RelativeLayout	layoutE			= null;
	private SparseIntArray	listTestData	= null;
	private int				mnLevel			= 0;
	private Handler			notifyHandler	= null;

	public EyeTestController(Activity activity)
	{
		super();
		theActivity = activity;
		initView(activity);

		listTestData = new SparseIntArray();
		listTestData.put(0, R.drawable.big_e_right);
		listTestData.put(1, R.drawable.big_e_up);
		listTestData.put(2, R.drawable.big_e_down);
		listTestData.put(3, R.drawable.big_e_left);
		listTestData.put(4, R.drawable.big_e_right);
		listTestData.put(5, R.drawable.big_e_left);
		listTestData.put(6, R.drawable.big_e_down);
		listTestData.put(7, R.drawable.big_e_up);

	}

	@Override
	protected void finalize() throws Throwable
	{
		listTestData.clear();
		listTestData = null;
		super.finalize();
	}

	public void setNotifyHandler(Handler handler)
	{
		notifyHandler = handler;
	}

	private void initView(Activity activity)
	{
		imgViewE = (ImageView) activity.findViewById(R.id.imageViewE);
		imgViewUp = (ImageView) activity.findViewById(R.id.imageViewArrowUp);
		imgViewDown = (ImageView) activity.findViewById(R.id.imageViewArrowDown);
		imgViewLeft = (ImageView) activity.findViewById(R.id.imageViewArrowLeft);
		imgViewRight = (ImageView) activity.findViewById(R.id.imageViewArrowRight);
		imgClose = (ImageView) activity.findViewById(R.id.imageViewClose);
		layoutE = (RelativeLayout) activity.findViewById(R.id.relativeLayoutEMain);

		setTouchEvent(imgViewE);
		setTouchEvent(imgViewUp);
		setTouchEvent(imgViewDown);
		setTouchEvent(imgViewLeft);
		setTouchEvent(imgViewRight);
		setTouchEvent(imgClose);
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
						setColorFilter((ImageView) v, "#CCC7F50E");
					}
					break;
				case MotionEvent.ACTION_UP:
					if (v instanceof ImageView)
					{
						setColorFilter((ImageView) v, Color.TRANSPARENT);
						if (R.id.imageViewClose == v.getId())
						{
							close();
						}
						else
						{
							checkAnswer(v.getId());
						}
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					if (v instanceof ImageView)
					{
						setColorFilter((ImageView) v, Color.TRANSPARENT);
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

	private void setLevel(int nLevel)
	{
		int nEResId = listTestData.get(nLevel);
		int nPadding = 0;
		imgViewE.setImageResource(nEResId);
		Device device = new Device(theActivity);
		switch (nLevel)
		{
		case 1:
			nPadding = device.ScaleSize(40);
			break;
		case 2:
			nPadding = device.ScaleSize(70);
			break;
		case 3:
			nPadding = device.ScaleSize(90);
			break;
		case 4:
			nPadding = device.ScaleSize(140);
			break;
		case 5:
			nPadding = device.ScaleSize(160);
			break;
		case 6:
			nPadding = device.ScaleSize(185);
			break;
		case 7:
			nPadding = device.ScaleSize(198);
			break;
		}

		device = null;
		layoutE.setPadding(nPadding, nPadding, nPadding, nPadding);
	}

	private void checkAnswer(int nArrow)
	{
		switch (nArrow)
		{
		case R.id.imageViewArrowRight:
			break;
		case R.id.imageViewArrowUp:
			break;
		case R.id.imageViewArrowLeft:
			break;
		case R.id.imageViewArrowDown:
			break;
		}
		++mnLevel;
		if (7 < mnLevel)
		{
			mnLevel = 0;
			close();
		}
		setLevel(mnLevel);
	}

	private void close()
	{
		EventHandler.notify(notifyHandler, EventMessage.MSG_FLIPPER_CLOSE, 0, 0, null);
	}
}
