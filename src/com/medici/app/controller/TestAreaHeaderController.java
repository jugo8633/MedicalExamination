package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.ImageViewTouchHandler;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TestAreaHeaderController
{

	private final int				RES_ID_INFO			= R.id.imageViewTestAreaHeaderInfo;
	private final int				RES_ID_CLOSE		= R.id.imageViewTestAreaHeaderClose;
	private final int				RES_ID_LAYOUT		= R.id.RelativeLayoutTestAreaHeaderMain;
	private ViewGroup				mainLayout			= null;
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
		mainLayout = (ViewGroup) parent.findViewById(RES_ID_LAYOUT);
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

	public void hideHeader(boolean bHide)
	{
		if (bHide)
		{
			mainLayout.setVisibility(View.INVISIBLE);
		}
		else
		{
			mainLayout.setVisibility(View.VISIBLE);
		}
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
