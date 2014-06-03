package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class Comprehension1Controller extends TestAreaController
{

	private int[]	listImgViewResId	= { R.id.imageViewComprehension1OK };

	public Comprehension1Controller(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity, handler);
	}

	@Override
	protected boolean onClose()
	{
		return false;
	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutComprehension1Main);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);
		NumberPicker numberPicker = (NumberPicker) parent.findViewById(R.id.numberPickerEgg);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(9);
		
		numberPicker = (NumberPicker) parent.findViewById(R.id.numberPickerApple);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(9);
	}

	private void onButtonClicked(int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewComprehension1OK:
			close();
			break;
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
											onButtonClicked(msg.arg1);
											break;
										}
									}
								};

	@Override
	protected void init()
	{

	}
}
