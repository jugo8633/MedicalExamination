package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.Logs;
import com.medical.medicalexamination.model.Type;

import android.app.Activity;
import android.os.Handler;

public class ComprehensionTestPageController extends TestListController
{
	private int		TEST_EXPRESSION	= Type.INVALID;
	private int		TEST_PUZZLE		= Type.INVALID;
	private int		TEST_WORD		= Type.INVALID;
	private Handler	theHandler		= null;

	public ComprehensionTestPageController(Activity activity, Handler handler)
	{
		super(activity, R.id.listViewComprehensionTest);
		TEST_EXPRESSION = super.addItem(R.drawable.expression, null);
		TEST_PUZZLE = super.addItem(R.drawable.puzzle, null);
		TEST_WORD = super.addItem(R.drawable.word, null);
		super.updateList();

		theHandler = handler;
	}

	private void notifyTestItemSelected(int nPosition)
	{
		int nWhat = Type.INVALID;
		if (TEST_EXPRESSION == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_EXPRESSION;
		}
		else if (TEST_PUZZLE == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_PUZZLE;
		}
		else if (TEST_WORD == nPosition)
		{
			nWhat = EventMessage.MSG_SHOW_TEST_WORD;
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
