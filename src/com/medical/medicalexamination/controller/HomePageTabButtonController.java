package com.medical.medicalexamination.controller;

import android.app.Activity;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.view.TabButton;

public class HomePageTabButtonController
{
	private TabButton	tabButton	= null;

	public HomePageTabButtonController(Activity activity)
	{
		super();
		tabButton = (TabButton) activity.findViewById(R.id.tabButtonHomePageMedicalTest);
	}
}
