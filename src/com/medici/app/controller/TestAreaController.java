package com.medici.app.controller;

import com.medici.app.model.ImageViewTouchHandler;

import android.app.Activity;
import android.os.Handler;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class TestAreaController
{

	protected Activity					theActivity			= null;
	protected Handler					notifyHandler		= null;
	private TestAreaHeaderController	headerController	= null;
	private ImageViewTouchHandler		imageViewHandler	= null;
	private SparseIntArray				listResId			= null;
	private boolean						mbIsExamination		= false;

	public TestAreaController(Activity activity, Handler handler)
	{
		super();
		theActivity = activity;
		notifyHandler = handler;
		imageViewHandler = new ImageViewTouchHandler();
		listResId = new SparseIntArray();
	}

	@Override
	protected void finalize() throws Throwable
	{
		listResId.clear();
		listResId = null;
		headerController = null;
		imageViewHandler = null;
		super.finalize();
	}

	public abstract void init();

	public abstract void release();

	protected abstract boolean onClose();

	protected abstract boolean onInfo();

	protected void initHeader(ViewGroup parent)
	{
		if (null != parent)
		{
			headerController = new TestAreaHeaderController(parent, notifyHandler);
			headerController.setOnClosedListener(new TestAreaHeaderController.OnClosedListener()
			{
				@Override
				public void onClosed()
				{
					onClose();
				}
			});
			headerController.setOnInfoedListener(new TestAreaHeaderController.OnInfoedListener()
			{
				@Override
				public void onInfoed()
				{
					onInfo();
				}
			});
		}
	}

	protected void hideHeader(boolean bHide)
	{
		if (null != headerController)
		{
			headerController.hideHeader(bHide);
		}
	}

	protected void close()
	{
		headerController.close();
	}

	protected void setTouchEvent(View view, final Handler handler)
	{
		imageViewHandler.setTouchEvent(view, handler);
	}

	protected void addImageViewResId(ViewGroup parent, int[] nResIds, Handler handler)
	{
		ImageView imgView = null;
		for (int i = 0; i < nResIds.length; ++i)
		{
			imgView = (ImageView) parent.findViewById(nResIds[i]);
			setTouchEvent(imgView, handler);
		}
	}

	protected void setExaminationMode(boolean bIs)
	{
		mbIsExamination = bIs;
	}

	protected boolean getExaminationMode()
	{
		return mbIsExamination;
	}
}
