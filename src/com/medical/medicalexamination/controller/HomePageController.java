package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.Logs;
import com.medical.medicalexamination.view.TabButton;

import android.app.Activity;
import android.view.ViewGroup;

public class HomePageController
{
	private ViewGroup	layoutGroup	= null;

	public HomePageController(Activity activity)
	{
		super();
		layoutGroup = (ViewGroup) activity.findViewById(R.id.RelativeLayoutHomePage);
		if (null != layoutGroup)
		{
			initTabButton(activity, layoutGroup);
		}
	}

	private void initTabButton(Activity activity, ViewGroup parent)
	{
		TabButton tabButton = (TabButton) parent.findViewById(R.id.tabButtonHomePageMedicalTest);
		if (null != tabButton)
		{
			tabButton.addTextButton(activity.getString(R.string.basic_test));
			tabButton.addTextButton(activity.getString(R.string.comprehension));
			tabButton.addTextButton(activity.getString(R.string.memory));
		}
	}

}
