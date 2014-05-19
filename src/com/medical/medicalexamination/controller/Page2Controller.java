package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Page2Controller
{
	private View		viewPage2	= null;
	private ImageView	imageEye	= null;

	public Page2Controller(View view)
	{
		super();
		viewPage2 = view;
		imageEye = (ImageView) view.findViewById(R.id.imageViewEyeTest);
		imageEye.setOnClickListener(buttonClickListener);
	}

	
	private OnClickListener	buttonClickListener	= new OnClickListener()
												{

													@Override
													public void onClick(View v)
													{
														switch (v.getId())
														{
														case R.id.imageViewEyeTest:
															break;
														}
													}
												};
}
