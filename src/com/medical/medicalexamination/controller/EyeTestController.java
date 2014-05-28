package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Device;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.ImageViewTouchHandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EyeTestController
{
	private Activity				theActivity			= null;
	private ImageView				imgViewE			= null;
	private ImageView				imgViewUp			= null;
	private ImageView				imgViewDown			= null;
	private ImageView				imgViewLeft			= null;
	private ImageView				imgViewRight		= null;
	private ImageView				imgClose			= null;
	private RelativeLayout			layoutE				= null;
	private SparseIntArray			listTestData		= null;
	private int						mnLevel				= 0;
	private Handler					notifyHandler		= null;
	private ImageViewTouchHandler	imageViewHandler	= null;

	public EyeTestController(Activity activity)
	{
		super();
		theActivity = activity;

		imageViewHandler = new ImageViewTouchHandler();
		listTestData = new SparseIntArray();
		listTestData.put(0, R.drawable.big_e_right);
		listTestData.put(1, R.drawable.big_e_up);
		listTestData.put(2, R.drawable.big_e_down);
		listTestData.put(3, R.drawable.big_e_left);
		listTestData.put(4, R.drawable.big_e_right);
		listTestData.put(5, R.drawable.big_e_left);
		listTestData.put(6, R.drawable.big_e_down);
		listTestData.put(7, R.drawable.big_e_up);

		initView(activity);
	}

	@Override
	protected void finalize() throws Throwable
	{
		listTestData.clear();
		listTestData = null;
		imageViewHandler = null;
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
		imgClose = (ImageView) activity.findViewById(R.id.imageViewEyeClose);
		layoutE = (RelativeLayout) activity.findViewById(R.id.relativeLayoutEMain);

		imageViewHandler.setTouchEvent(imgViewUp, selfHandler);
		imageViewHandler.setTouchEvent(imgViewDown, selfHandler);
		imageViewHandler.setTouchEvent(imgViewLeft, selfHandler);
		imageViewHandler.setTouchEvent(imgViewRight, selfHandler);
		imageViewHandler.setTouchEvent(imgClose, selfHandler);
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
		case R.id.imageViewHearLeft:
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

	private void touchHandler(final int nResId)
	{
		if (R.id.imageViewEyeClose == nResId)
		{
			close();
		}
		else
		{
			checkAnswer(nResId);
		}
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_SELECTED:
											touchHandler(msg.arg1);
											break;
										}
									}
								};
}
