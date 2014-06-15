package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.view.AnimationType;
import com.medici.app.view.TabButton;

import android.app.Activity;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class HomePageController
{
	private ViewFlipper						flipperTest			= null;
	private TabButton						tabButton			= null;
	@SuppressWarnings("unused")
	private BasicTestPageController			basicTestPage		= null;
	@SuppressWarnings("unused")
	private ComprehensionTestPageController	comprehensionPage	= null;
	@SuppressWarnings("unused")
	private MemoryTestPageController		memoryPage			= null;

	public HomePageController(Activity activity, Handler handler)
	{
		super();

		ViewGroup layoutGroup = (ViewGroup) activity.findViewById(R.id.RelativeLayoutHomePage);
		if (null != layoutGroup)
		{
			initTabButton(activity, layoutGroup);
			AnimationType animationType = new AnimationType();
			flipperTest = (ViewFlipper) layoutGroup.findViewById(R.id.ViewFlipperHomePageTestArea);
			flipperTest.setInAnimation(animationType.inFromRightAnimation(200));
			flipperTest.setOutAnimation(animationType.outToLeftAnimation(200));
			animationType = null;
		}

		basicTestPage = new BasicTestPageController(activity, handler);
		comprehensionPage = new ComprehensionTestPageController(activity, handler);
		memoryPage = new MemoryTestPageController(activity, handler);
	}

	private void initTabButton(Activity activity, ViewGroup parent)
	{
		tabButton = (TabButton) parent.findViewById(R.id.tabButtonHomePageMedicalTest);
		if (null != tabButton)
		{
			tabButton.addTextButton(activity.getString(R.string.basic_test));
			tabButton.addTextButton(activity.getString(R.string.comprehension));
			tabButton.addTextButton(activity.getString(R.string.memory));
			tabButton.setOnItemSwitchedListener(new TabButton.OnItemSwitchedListener()
			{
				@Override
				public void onItemSwitched(int nIndex)
				{
					flipperTest.setDisplayedChild(nIndex);
				}
			});
		}
	}

	public void setTabButton(int nIndex)
	{
		tabButton.setItemSelect(nIndex);
	}
}
