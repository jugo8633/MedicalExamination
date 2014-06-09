package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.Logs;
import com.medical.medicalexamination.view.TabButton;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class HomePageController
{
	private ViewGroup		layoutGroup			= null;
	private TabButton		tabButton			= null;
	private ListView		listViewBasicTest	= null;
	private LayoutInflater	inflater			= null;

	public HomePageController(Activity activity)
	{
		super();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutGroup = (ViewGroup) activity.findViewById(R.id.RelativeLayoutHomePage);
		if (null != layoutGroup)
		{
			initTabButton(activity, layoutGroup);
			initListView(activity, layoutGroup);
		}
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

	private void initListView(Activity activity, ViewGroup parent)
	{
		listViewBasicTest = (ListView) parent.findViewById(R.id.listViewBasicTest);
		listViewBasicTest.setAdapter(new BasicTestListAdapter());
		listViewBasicTest.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				EventHandler.notify(theHandler,
						EventMessage.MSG_TEST_SELECTED, v.getId(), 0, null);
			}
		});
	}

	private class BasicTestListAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return 3;
		}

		@Override
		public Object getItem(int position)
		{
			return position;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view = inflater.inflate(R.layout.medical_test_item, null);
			RelativeLayout layoutIconBackgroun = (RelativeLayout) view
					.findViewById(R.id.RelativeLayoutTestIconBackgroun);
			ImageView imageIcon = (ImageView) view.findViewById(R.id.imageViewTestIcon);
			int nItem = position % 3;
			switch (nItem)
			{
			case 0:
				layoutIconBackgroun.setBackgroundResource(R.drawable.arrow_yellow);
				imageIcon.setImageResource(R.drawable.sight);
				break;
			case 1:
				layoutIconBackgroun.setBackgroundResource(R.drawable.arrow_blue);
				imageIcon.setImageResource(R.drawable.headphones);
				break;
			case 2:
				layoutIconBackgroun.setBackgroundResource(R.drawable.arrow_red);
				imageIcon.setImageResource(R.drawable.hand_shake);
				break;
			default:
				layoutIconBackgroun.setBackgroundResource(R.drawable.arrow_yellow);
				break;
			}

			return view;
		}

	}

}
