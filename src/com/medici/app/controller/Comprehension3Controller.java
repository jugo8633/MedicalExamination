package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class Comprehension3Controller extends TestAreaController
{
	private NumberPicker	numberPicker1_1		= null;
	private NumberPicker	numberPicker1_2		= null;
	private NumberPicker	numberPicker2_1		= null;
	private NumberPicker	numberPicker2_2		= null;
	private int[]			listImgViewResId	= { R.id.imageViewComprehension1OK };

	public Comprehension3Controller(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity, handler);
	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutComprehension3Main);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);

		numberPicker1_1 = (NumberPicker) parent.findViewById(R.id.numberPickerNumber1_1);
		numberPicker1_2 = (NumberPicker) parent.findViewById(R.id.numberPickerNumber1_2);
		numberPicker2_1 = (NumberPicker) parent.findViewById(R.id.numberPickerNumber2_1);
		numberPicker2_2 = (NumberPicker) parent.findViewById(R.id.numberPickerNumber2_2);

		numberPicker1_1.setMinValue(0);
		numberPicker1_1.setMaxValue(9);

		numberPicker1_2.setMinValue(0);
		numberPicker1_2.setMaxValue(9);

		numberPicker2_1.setMinValue(0);
		numberPicker2_1.setMaxValue(9);

		numberPicker2_2.setMinValue(0);
		numberPicker2_2.setMaxValue(9);
	}

	@Override
	protected void init()
	{
		numberPicker1_1.setValue(0);
		numberPicker1_2.setValue(0);
		numberPicker2_1.setValue(0);
		numberPicker2_2.setValue(0);
	}

	@Override
	protected boolean onClose()
	{
		return false;
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
	protected boolean onInfo()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
