package com.medical.medicalexamination;

import java.util.ArrayList;
import java.util.List;

import com.medical.medicalexamination.controller.EyeTestController;
import com.medical.medicalexamination.controller.FlipperMenuController;
import com.medical.medicalexamination.controller.Page2Controller;
import com.medical.medicalexamination.controller.SlideMenuController;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.SqliteHandler;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity
{
	private DrawerLayout			drawerLayout			= null;
	private ImageView				listMenuBtn				= null;
	private View					view1, view2, view3;
	private List<View>				viewList;
	private ViewPager				viewPager				= null;
	private Page2Controller			page2Controller			= null;
	private SlideMenuController		menuHandler				= null;
	private FlipperMenuController	flipperMenuController	= null;

	//private SqliteHandler		sqliteHandler		= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/** init orientation*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/** load content */
		setContentView(R.layout.activity_main);

		/** init sqlite */
		//sqliteHandler = new SqliteHandler(this);

		/** init Drawer Layout */
		initDrawerLayout();

		/** init viewpager */
		initViewPager();

		/** init menu */
		menuHandler = new SlideMenuController(this, selfHandler);

		/** init flipper menu */
		flipperMenuController = new FlipperMenuController(this, selfHandler);

		/** show login */
		flipperMenuController.setHideEnable(false);
		flipperMenuController.showLogin();

	}

	private void initDrawerLayout()
	{
		listMenuBtn = (ImageView) this.findViewById(R.id.listMenuBtn);
		listMenuBtn.setOnClickListener(buttonClick);
		drawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		drawerLayout.setDrawerListener(new DrawerListener()
		{
			@Override
			public void onDrawerClosed(View arg0)
			{
				drawerHandler(false);
			}

			@Override
			public void onDrawerOpened(View arg0)
			{
				drawerHandler(true);
			}

			@Override
			public void onDrawerSlide(View arg0, float arg1)
			{

			}

			@Override
			public void onDrawerStateChanged(int arg0)
			{

			}
		});
	}

	private void drawerHandler(boolean bOpen)
	{
		if (bOpen)
		{
			listMenuBtn.setImageResource(R.drawable.list_click);
		}
		else
		{
			listMenuBtn.setImageResource(R.drawable.list_normal);
		}
	}

	private void initViewPager()
	{
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		LayoutInflater lf = LayoutInflater.from(this);
		view1 = lf.inflate(R.layout.page01, null);
		view2 = lf.inflate(R.layout.page02, null);
		view3 = lf.inflate(R.layout.page03, null);

		page2Controller = new Page2Controller(view2, selfHandler);

		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		PagerAdapter pagerAdapter = new PagerAdapter()
		{
			@Override
			public int getCount()
			{
				return viewList.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object)
			{
				container.removeView(viewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				container.addView(viewList.get(position));
				return viewList.get(position);
			}

		};

		viewPager.setAdapter(pagerAdapter);

		/** init focuse */
		view1.findViewById(R.id.editTextName).requestFocus();

	}

	private void handleButtonClick(View view)
	{
		if (view.getId() == R.id.listMenuBtn)
		{
			if (drawerLayout.isDrawerOpen(Gravity.LEFT))
			{
				drawerLayout.closeDrawers();
			}
			else
			{
				drawerLayout.openDrawer(Gravity.LEFT);
			}
		}
	}

	private void showSensorActivity()
	{
		Intent intent = new Intent(MainActivity.this, SensorActivity.class);
		startActivity(intent);
	}

	private void showTest(int nButtonId)
	{
		switch (nButtonId)
		{
		case R.id.imageViewEyeTest:
			flipperMenuController.showEyeTest();
			break;
		case R.id.imageViewHearTest:
			flipperMenuController.showHearTest();
			break;
		case R.id.imageViewFinger:
			showSensorActivity();
			break;
		case R.id.imageViewComprehensionTest1:
			flipperMenuController.showComprehension1();
			break;
		case R.id.imageViewComprehensionTest2:
			flipperMenuController.showComprehension2();
			break;
		case R.id.imageViewComprehensionTest3:
			flipperMenuController.showComprehension3();
			break;
		case R.id.imageViewMemoryTest1:
			flipperMenuController.showMemory1();
			break;
		case R.id.imageViewMemoryTest2:
			break;
		case R.id.imageViewMemoryTest3:
			break;
		}
	}

	private OnClickListener	buttonClick	= new OnClickListener()
										{
											@Override
											public void onClick(View view)
											{
												handleButtonClick(view);
											}
										};

	private Handler			selfHandler	= new Handler()
										{

											@Override
											public void handleMessage(Message msg)
											{
												switch (msg.what)
												{
												case EventMessage.MSG_LOGIN: // login success
													flipperMenuController.setHideEnable(true);
													flipperMenuController.close();
													break;
												case EventMessage.MSG_SHOW_LOGIN:
													flipperMenuController.showLogin();
													break;
												case EventMessage.MSG_SHOW_HISTORY:
													flipperMenuController.showCalendar();
													break;
												case EventMessage.MSG_TEST_SELECTED:
													showTest(msg.arg1);
													break;

												}
											}

										};

}
