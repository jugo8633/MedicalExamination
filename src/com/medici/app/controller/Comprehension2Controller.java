package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.EditText;

public class Comprehension2Controller extends TestAreaController
{
	private EditText	editText1			= null;
	private EditText	editText2			= null;
	private int[]		listImgViewResId	= { R.id.imageViewComprehension1OK };

	public Comprehension2Controller(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity, handler);
	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutComprehension2Main);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);

		editText1 = (EditText) parent.findViewById(R.id.editTextLion);
		editText2 = (EditText) parent.findViewById(R.id.editTextCat);
	}

	@Override
	protected void init()
	{
		editText1.setText(null);
		editText2.setText(null);
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

}
