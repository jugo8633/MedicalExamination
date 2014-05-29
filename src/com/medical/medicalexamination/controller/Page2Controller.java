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
	private Handler	theHandler	= null;

	public Page2Controller(View view, Handler handler)
	{
		super();
		theHandler = handler;
		ImageView imageView = null;

		imageView = (ImageView) view.findViewById(R.id.imageViewEyeTest);
		imageView.setOnClickListener(buttonClickListener);

		imageView = (ImageView) view.findViewById(R.id.imageViewHearTest);
		imageView.setOnClickListener(buttonClickListener);

		imageView = (ImageView) view.findViewById(R.id.imageViewFinger);
		imageView.setOnClickListener(buttonClickListener);

	}

	private OnClickListener	buttonClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														switch (v.getId())
														{
														case R.id.imageViewEyeTest:
															EventHandler.notify(theHandler, EventMessage.MSG_TEST_EYE,
																	0, 0, null);
															break;
														case R.id.imageViewHearTest:
															EventHandler.notify(theHandler, EventMessage.MSG_TEST_HEAR,
																	0, 0, null);
															break;
														case R.id.imageViewFinger:
															EventHandler.notify(theHandler, EventMessage.MSG_TEST_ABSORPTION,
																	0, 0, null);
															break;
														}
													}
												};
}
