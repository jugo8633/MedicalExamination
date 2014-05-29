package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Device;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.SparseIntArray;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EyeTestController extends TestAreaController
{
	private ImageView		imgViewE			= null;
	private RelativeLayout	layoutE				= null;
	private SparseIntArray	listTestData		= null;
	private int				mnLevel				= 0;

	private int[]			listImgViewResId	= { R.id.imageViewArrowUp, R.id.imageViewArrowDown,
			R.id.imageViewArrowLeft, R.id.imageViewArrowRight };

	public EyeTestController(Activity activity, Handler handler)
	{
		super(activity, handler);

		listTestData = new SparseIntArray();
		listTestData.put(0, R.drawable.big_e_right);
		listTestData.put(1, R.drawable.big_e_up);
		listTestData.put(2, R.drawable.big_e_down);
		listTestData.put(3, R.drawable.big_e_left);
		listTestData.put(4, R.drawable.big_e_right);
		listTestData.put(5, R.drawable.big_e_left);
		listTestData.put(6, R.drawable.big_e_down);
		listTestData.put(7, R.drawable.big_e_up);

		initView(activity, handler);
	}

	@Override
	protected void finalize() throws Throwable
	{
		listTestData.clear();
		listTestData = null;
		super.finalize();
	}

	private void initView(Activity activity, Handler handler)
	{
		RelativeLayout eyeTestLayout = (RelativeLayout) activity.findViewById(R.id.eyetest_main_layout);
		initHeader(eyeTestLayout);
		imgViewE = (ImageView) eyeTestLayout.findViewById(R.id.imageViewE);
		layoutE = (RelativeLayout) eyeTestLayout.findViewById(R.id.relativeLayoutEMain);
		addImageViewResId(eyeTestLayout, listImgViewResId, selfHandler);
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

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_SELECTED:
											checkAnswer(msg.arg1);
											break;
										}
									}
								};
}
