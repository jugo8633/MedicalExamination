package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Device;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.ImageViewTouchHandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class HearTestController
{
	private Activity				theActivity			= null;
	private Handler					notifyHandler		= null;
	private ImageView				imgViewSpeakerLeft	= null;
	private ImageView				imgViewSpeakerRight	= null;
	private ImageView				imgViewLeft			= null;
	private ImageView				imgViewRight		= null;
	private ImageView				imgClose			= null;
	private ImageViewTouchHandler	imageViewHandler	= null;
	private int						mnLevel				= 0;

	public HearTestController(Activity activity)
	{
		super();
		theActivity = activity;

		imageViewHandler = new ImageViewTouchHandler();
		initView(activity);
	}

	@Override
	protected void finalize() throws Throwable
	{
		imageViewHandler = null;
		super.finalize();
	}

	public void setNotifyHandler(Handler handler)
	{
		notifyHandler = handler;
	}

	private void initView(Activity activity)
	{
		imgViewSpeakerLeft = (ImageView) activity.findViewById(R.id.imageViewSpeakerLeft);
		imgViewSpeakerRight = (ImageView) activity.findViewById(R.id.imageViewSpeakerRight);
		imgViewLeft = (ImageView) activity.findViewById(R.id.imageViewHearLeft);
		imgViewRight = (ImageView) activity.findViewById(R.id.imageViewHearRight);
		imgClose = (ImageView) activity.findViewById(R.id.imageViewClose);

		imageViewHandler.setTouchEvent(imgViewLeft, selfHandler);
		imageViewHandler.setTouchEvent(imgViewRight, selfHandler);
		imageViewHandler.setTouchEvent(imgClose, selfHandler);
	}

	private void checkAnswer(int nArrow)
	{
		switch (nArrow)
		{
		case R.id.imageViewHearLeft:
			break;
		case R.id.imageViewHearRight:
			break;
		}
		++mnLevel;
		if (5 < mnLevel)
		{
			mnLevel = 0;
			close();
		}
		setLevel(mnLevel);
	}

	private void setLevel(int nLevel)
	{

		switch (nLevel)
		{
		case 1:
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;

		}

	}

	private void close()
	{
		EventHandler.notify(notifyHandler, EventMessage.MSG_FLIPPER_CLOSE, 0, 0, null);
	}

	private void touchHandler(final int nResId)
	{
		if (R.id.imageViewClose == nResId)
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
