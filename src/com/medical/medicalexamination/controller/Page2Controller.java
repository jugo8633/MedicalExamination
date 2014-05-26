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
	private View		viewPage2	= null;
	private ImageView	imageEye	= null;
	private Handler		theHandler	= null;

	public Page2Controller(View view, Handler handler)
	{
		super();
		viewPage2 = view;
		imageEye = (ImageView) view.findViewById(R.id.imageViewEyeTest);
		imageEye.setOnClickListener(buttonClickListener);
		theHandler = handler;
	}

	private OnClickListener	buttonClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														switch (v.getId())
														{
														case R.id.imageViewEyeTest:
															EventHandler.notify(theHandler, EventMessage.MSG_TEST_EYE, 0, 0, null);
															break;
														}
													}
												};
}
