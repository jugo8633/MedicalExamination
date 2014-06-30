package com.medici.app.controller;

import com.jugo.smartwidget.common.Logs;
import com.jugo.smartwidget.common.Utility;
import com.jugo.smartwidget.image.BitmapHandler;
import com.medici.app.R;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.model.Type;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AttentionExamController extends TestAreaController
{
	private int			mnLevel				= 0;
	private int			mnExamResult		= 0;
	private int[]		listImgViewResId	= { R.id.imageViewCheck };
	private Bitmap		bmpDay				= null;
	private Bitmap		bmpDayMosaic		= null;
	private ImageView	ivDay				= null;

	public AttentionExamController(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity);
	}

	private void initView(Activity activity)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutAttentionMain);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);
		ivDay = (ImageView) parent.findViewById(R.id.imageViewDay);
	}

	private void showInfo()
	{
		Global.showDidlog(theActivity, selfHandler, null, Global.str(R.string.attention_time_info), Type.INVALID);
	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	private void setMosaic(int nLevel)
	{
		releaseAllBitmap();
		bmpDay = BitmapFactory.decodeResource(theActivity.getResources(), R.drawable.day13);
		Rect rect = Utility.getViewRect(ivDay);
		bmpDayMosaic = BitmapHandler.makeMosaic(bmpDay, rect, 45);
		ivDay.setImageBitmap(bmpDayMosaic);
	}

	private void releaseAllBitmap()
	{
		BitmapHandler.releaseBitmap(bmpDay);
		BitmapHandler.releaseBitmap(bmpDayMosaic);
	}

	@Override
	public void init()
	{
		release();
		showInfo();
		setMosaic(mnLevel);
	}

	@Override
	public void release()
	{
		mnLevel = 0;
		mnExamResult = 0;
	}

	@Override
	protected boolean onClose()
	{
		return false;
	}

	@Override
	protected boolean onInfo()
	{
		showInfo();
		return false;
	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewCheck:
			break;

		}
	}

	private void onTimer(int nId)
	{

	}

	private void onDialog(int nId)
	{
		if (EventMessage.MSG_DIALOG_CLOSE_RESULT == nId)
		{
			close();
		}

		if (EventMessage.MSG_DIALOG_CLOSE_INFO == nId)
		{

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
										case EventMessage.MSG_CLOSE_MESSAGE_DIALOG:
											onDialog(msg.arg1);
											break;
										case EventMessage.MSG_ON_TIMER:
											onTimer(msg.arg1);
											break;
										}
									}
								};

}
