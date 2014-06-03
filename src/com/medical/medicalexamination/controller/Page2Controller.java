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
		
		imageView = (ImageView)view.findViewById(R.id.imageViewComprehensionTest1);
		imageView.setOnClickListener(buttonClickListener);
		
		imageView = (ImageView)view.findViewById(R.id.imageViewComprehensionTest2);
		imageView.setOnClickListener(buttonClickListener);
		
		imageView = (ImageView)view.findViewById(R.id.imageViewComprehensionTest3);
		imageView.setOnClickListener(buttonClickListener);

	}

	private OnClickListener	buttonClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														if(v instanceof ImageView)
														{
															EventHandler.notify(theHandler, EventMessage.MSG_TEST_SELECTED,
																	v.getId(), 0, null);
														}
													}
												};
}
