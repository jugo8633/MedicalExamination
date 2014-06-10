package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.view.TabButton;

import android.app.Activity;
import android.os.Handler;
import android.view.ViewGroup;

public class HomePageController
{
	private TabButton				tabButton		= null;
	private BasicTestPageController	basicTestPage	= null;

	public HomePageController(Activity activity, Handler handler)
	{
		super();

		ViewGroup layoutGroup = (ViewGroup) activity.findViewById(R.id.RelativeLayoutHomePage);
		if (null != layoutGroup)
		{
			initTabButton(activity, layoutGroup);
		}

		basicTestPage = new BasicTestPageController(activity, handler);
	}

	private void initTabButton(Activity activity, ViewGroup parent)
	{
		tabButton = (TabButton) parent.findViewById(R.id.tabButtonHomePageMedicalTest);
		if (null != tabButton)
		{
			tabButton.addTextButton(activity.getString(R.string.basic_test));
			tabButton.addTextButton(activity.getString(R.string.comprehension));
			tabButton.addTextButton(activity.getString(R.string.memory));
		}
	}

	public void setTabButton(int nIndex)
	{
		tabButton.setItemSelect(nIndex);
	}
}
