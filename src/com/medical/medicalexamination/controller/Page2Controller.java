package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Page2Controller
{
	private Handler		theHandler	= null;

	private final int[]	listButton	= { R.id.imageViewEyeTest, R.id.imageViewHearTest, R.id.imageViewFinger,
			R.id.imageViewComprehensionTest1, R.id.imageViewComprehensionTest2, R.id.imageViewComprehensionTest3,
			R.id.imageViewMemoryTest1, R.id.imageViewMemoryTest2, R.id.imageViewMemoryTest3 };

	public Page2Controller(View view, Handler handler)
	{
		super();
		theHandler = handler;
		for (int i = 0; i < listButton.length; ++i)
		{
			view.findViewById(listButton[i]).setOnClickListener(buttonClickListener);
		}
	}

	private OnClickListener	buttonClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														if (v instanceof ImageView)
														{
															EventHandler.notify(theHandler,
																	EventMessage.MSG_TEST_SELECTED, v.getId(), 0, null);
														}
													}
												};
}
