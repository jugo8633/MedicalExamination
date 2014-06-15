package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

public class Memory2Controller extends TestAreaController
{
	private ViewGroup	parent				= null;
	private int[]		listImgViewResId	= { R.id.imageViewComprehension1OK };

	public Memory2Controller(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity, handler);
	}

	@Override
	protected void init()
	{

	}

	@Override
	protected boolean onClose()
	{
		return false;
	}

	private void initView(Activity activity, Handler handler)
	{
		parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutMemory2Main);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);
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
