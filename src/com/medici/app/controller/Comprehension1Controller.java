package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class Comprehension1Controller extends TestAreaController
{

	private NumberPicker	numberPicker1		= null;
	private NumberPicker	numberPicker2		= null;
	private int[]			listImgViewResId	= { R.id.imageViewComprehension1OK };

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
		numberPicker1 = (NumberPicker) parent.findViewById(R.id.numberPickerEgg);
		numberPicker1.setMinValue(0);
		numberPicker1.setMaxValue(9);

		numberPicker2 = (NumberPicker) parent.findViewById(R.id.numberPickerApple);
		numberPicker2.setMinValue(0);
		numberPicker2.setMaxValue(9);
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
		numberPicker1.setValue(0);
		numberPicker2.setValue(0);
	}

	@Override
	protected boolean onInfo()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
