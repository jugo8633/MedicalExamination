package com.medici.app;

import java.util.Locale;

import com.medici.app.controller.ExaminationPageController;
import com.medici.app.controller.FlipperMenuController;
import com.medici.app.controller.LeftDrawerMenuController;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity
{
	private DrawerLayout				drawerLayout			= null;
	private ImageView					listMenuBtn				= null;
	private LeftDrawerMenuController	menuHandler				= null;
	private FlipperMenuController		flipperMenuController	= null;
	private ExaminationPageController	examinationPage			= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/** init orientation*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/** load content */
		setContentView(R.layout.activity_main);

		/** init Left Drawer Layout */
		initDrawerLayout();

		/** init left drawer menu */
		menuHandler = new LeftDrawerMenuController(this, selfHandler);

		/** init flipper menu */
		flipperMenuController = new FlipperMenuController(this, selfHandler);

		/** init home page */
		examinationPage = new ExaminationPageController(this, selfHandler);

		getDeviceLanguage();

		/** show drawer left */
		drawerLayout.openDrawer(Gravity.LEFT);
	}

	private void getDeviceLanguage()
	{
		String strLanguage = Locale.getDefault().getLanguage();

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
			listMenuBtn.setColorFilter(Color.parseColor("#CCC7F50E"));
		}
		else
		{
			listMenuBtn.setColorFilter(Color.TRANSPARENT);
		}
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

	private void showPersonInfoActivity()
	{
		Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
		startActivity(intent);
	}

	private void showExaminationActivity()
	{
		Intent intent = new Intent(MainActivity.this, ExaminationActivity.class);
		startActivity(intent);
	}

	private void showLoginActivity()
	{
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
	}

	private void showHistoryActivity()
	{
		Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
		startActivity(intent);
	}

	private void showTest(int nWhat)
	{
		switch (nWhat)
		{
		case EventMessage.MSG_SHOW_TEST_EYE:
			flipperMenuController.showEyeTest();
			break;
		case EventMessage.MSG_SHOW_TEST_HEAR:
			flipperMenuController.showHearTest();
			break;
		case EventMessage.MSG_SHOW_TEST_TREMBLE:
			showSensorActivity();
			break;
		case EventMessage.MSG_SHOW_TEST_EXPRESSION:
			flipperMenuController.showComprehension1();
			break;
		case EventMessage.MSG_SHOW_TEST_PUZZLE:
			flipperMenuController.showComprehension2();
			break;
		case EventMessage.MSG_SHOW_TEST_WORD:
			flipperMenuController.showComprehension3();
			break;
		case EventMessage.MSG_SHOW_TEST_CARD:
			flipperMenuController.showMemory1();
			break;
		case EventMessage.MSG_SHOW_TEST_SHAPE:
			flipperMenuController.showMemory2();
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
													showLoginActivity();
													break;
												case EventMessage.MSG_SHOW_PERSON_INFO:
													showPersonInfoActivity();
													break;
												case EventMessage.MSG_SHOW_EXAMINATION:
													showExaminationActivity();
													break;
												case EventMessage.MSG_SHOW_HISTORY:
													showHistoryActivity();
													break;
												case EventMessage.MSG_SHOW_TEST_EYE:
												case EventMessage.MSG_SHOW_TEST_HEAR:
												case EventMessage.MSG_SHOW_TEST_TREMBLE:
												case EventMessage.MSG_SHOW_TEST_EXPRESSION:
												case EventMessage.MSG_SHOW_TEST_PUZZLE:
												case EventMessage.MSG_SHOW_TEST_WORD:
												case EventMessage.MSG_SHOW_TEST_CARD:
												case EventMessage.MSG_SHOW_TEST_SHAPE:
													showTest(msg.what);
													break;

												}
											}

										};

}
