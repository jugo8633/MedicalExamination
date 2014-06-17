package com.medici.app;

import java.util.Locale;

import com.medici.app.controller.ExaminationPageController;
import com.medici.app.controller.LeftDrawerMenuController;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.model.Type;

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
	private DrawerLayout				drawerLayout	= null;
	private ImageView					listMenuBtn		= null;
	private LeftDrawerMenuController	menuHandler		= null;
	private ExaminationPageController	examinationPage	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Global.theActivity = this;

		/** init orientation*/
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		/** load content */
		setContentView(R.layout.activity_main);

		/** init Left Drawer Layout */
		initDrawerLayout();

		/** init left drawer menu */
		menuHandler = new LeftDrawerMenuController(this, selfHandler);

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

	private void showPersonInfoActivity()
	{
		Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
		startActivity(intent);
	}

	private void showExaminationActivity(int nExam)
	{
		Intent intent = new Intent(MainActivity.this, ExaminationActivity.class);
		intent.putExtra("EXAM", nExam);
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

	private void showExam(int nExam)
	{
		if (examinationPage.EXAMINATION_EYE == nExam)
		{
			showExaminationActivity(0);
		}
		else if (examinationPage.EXAMINATION_HEAR == nExam)
		{
			showExaminationActivity(1);
		}
		else if (examinationPage.EXAMINATION_TREMBLE == nExam)
		{
			showExaminationActivity(2);
		}
		else if (examinationPage.EXAMINATION_ATTENTION == nExam)
		{

		}
		else if (examinationPage.EXAMINATION_LANGUAGE == nExam)
		{

		}
		else if (examinationPage.EXAMINATION_MEMORY == nExam)
		{

		}
		else if (examinationPage.EXAMINATION_SPATIAL == nExam)
		{

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
												case EventMessage.MSG_SHOW_LOGIN:
													showLoginActivity();
													break;
												case EventMessage.MSG_SHOW_PERSON_INFO:
													showPersonInfoActivity();
													break;
												case EventMessage.MSG_SHOW_EXAMINATION:
													showExaminationActivity(Type.INVALID);
													break;
												case EventMessage.MSG_SHOW_HISTORY:
													showHistoryActivity();
													break;
												case EventMessage.MSG_EXAM_SELECTED:
													showExam(msg.arg1);
													break;
												}
											}

										};

}
