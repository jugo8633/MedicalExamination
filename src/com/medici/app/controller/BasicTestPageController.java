package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Type;

import android.app.Activity;
import android.os.Handler;

public class BasicTestPageController extends TestListController
{
	private int		TEST_EYE		= Type.INVALID;
	private int		TEST_HEAR		= Type.INVALID;
	private int		TEST_TREMBLE	= Type.INVALID;
	private Handler	theHandler		= null;

	public BasicTestPageController(Activity activity, Handler handler)
	{
		super(activity, R.id.listViewBasicTest);
		TEST_EYE = super.addItem(R.drawable.sight, null);
		TEST_HEAR = super.addItem(R.drawable.headphones, null);
		TEST_TREMBLE = super.addItem(R.drawable.hand_shake, null);
		super.updateList();

		theHandler = handler;
	}

	private void notifyTestItemSelected(int nPosition)
	{
		int nWhat = Type.INVALID;
		if (TEST_EYE == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_EYE;
		}
		else if (TEST_HEAR == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_HEAR;
		}
		else if (TEST_TREMBLE == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_TREMBLE;
		}
		else
		{
			return;
		}

		EventHandler.notify(theHandler, nWhat, 0, 0, null);
	}

	@Override
	protected void onItemSelected(int nPosition)
	{
		notifyTestItemSelected(nPosition);
	}
}
