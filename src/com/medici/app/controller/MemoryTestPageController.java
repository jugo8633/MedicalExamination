package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Type;

import android.app.Activity;
import android.os.Handler;

public class MemoryTestPageController extends TestListController
{
	private int		TEST_CARD	= Type.INVALID;
	private int		TEST_SHAPE	= Type.INVALID;
	private Handler	theHandler	= null;

	public MemoryTestPageController(Activity activity, Handler handler)
	{
		super(activity, R.id.listViewMemoryTest);
		TEST_CARD = super.addItem(R.drawable.memory_card, null);
		TEST_SHAPE = super.addItem(R.drawable.triangle, null);
		super.updateList();

		theHandler = handler;
	}

	private void notifyTestItemSelected(int nPosition)
	{
		int nWhat = Type.INVALID;
		if (TEST_CARD == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_CARD;
		}
		else if (TEST_SHAPE == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_SHAPE;
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
