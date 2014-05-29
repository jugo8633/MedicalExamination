package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.ImageViewTouchHandler;
import com.medical.medicalexamination.model.Logs;

import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TestAreaHeaderController
{

	private final int				RES_ID_INFO			= R.id.imageViewTestAreaHeaderInfo;
	private final int				RES_ID_CLOSE		= R.id.imageViewTestAreaHeaderClose;
	private ImageView				imgViewInfo			= null;
	private ImageView				imgViewClose		= null;
	private Handler					notifyHandler		= null;
	private ImageViewTouchHandler	imageViewHandler	= null;
	private OnClosedListener		onClosedListener	= null;

	public interface OnClosedListener
	{
		void OnClosed();
	}

	public void setOnClosedListener(OnClosedListener listener)
	{
		if (null != listener)
		{
			onClosedListener = listener;
		}
	}

	public TestAreaHeaderController(ViewGroup parent, Handler handler)
	{
		super();
		imageViewHandler = new ImageViewTouchHandler();
		notifyHandler = handler;
		imgViewInfo = (ImageView) parent.findViewById(RES_ID_INFO);
		imgViewClose = (ImageView) parent.findViewById(RES_ID_CLOSE);
		imageViewHandler.setTouchEvent(imgViewInfo, selfHandler);
		imageViewHandler.setTouchEvent(imgViewClose, selfHandler);
	}

	@Override
	protected void finalize() throws Throwable
	{
		imageViewHandler = null;
		super.finalize();
	}

	public void close()
	{
		onClosedListener.OnClosed();
		EventHandler.notify(notifyHandler, EventMessage.MSG_FLIPPER_CLOSE, 0, 0, null);
	}

	private void info()
	{

	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case RES_ID_INFO:
			info();
			break;
		case RES_ID_CLOSE:
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
											touchHandler(msg.arg1);
											break;
										}
									}
								};
}
