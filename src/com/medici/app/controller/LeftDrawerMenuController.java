package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

public class LeftDrawerMenuController
{
	private Handler		notifyHandler	= null;
	private final int[]	listMenuButton	= { R.id.RelativeLayoutLeftDrawerLogin,
			R.id.RelativeLayoutLeftDrawerPersonInfo, R.id.RelativeLayoutLeftDrawerExamination,
			R.id.RelativeLayoutLeftDrawerHistory };

	public LeftDrawerMenuController(Activity activity, final Handler handler)
	{
		super();
		notifyHandler = handler;
		initView(activity);
	}

	private void initView(Activity activity)
	{
		for (int i = 0; i < listMenuButton.length; ++i)
		{
			activity.findViewById(listMenuButton[i]).setOnTouchListener(menuButtonTouchListener);
		}
		activity.findViewById(R.id.left_drawer_main).setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return true;
			}
		});
	}

	private void menuButtonHandler(int nResId)
	{
		switch (nResId)
		{
		case R.id.RelativeLayoutLeftDrawerLogin:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_LOGIN, 0, 0, null);
			break;
		case R.id.RelativeLayoutLeftDrawerPersonInfo:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_PERSON_INFO, 0, 0, null);
			break;
		case R.id.RelativeLayoutLeftDrawerExamination:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_EXAMINATION, 0, 0, null);
			break;
		case R.id.RelativeLayoutLeftDrawerHistory:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_HISTORY, 0, 0, null);
			break;
		}
	}

	private OnTouchListener	menuButtonTouchListener	= new OnTouchListener()
													{
														@Override
														public boolean onTouch(View v, MotionEvent event)
														{
															if (v instanceof RelativeLayout)
															{
																switch (event.getAction())
																{
																case MotionEvent.ACTION_DOWN:
																	v.animate()
																			.scaleY(0.8f)
																			.setDuration(100)
																			.setInterpolator(
																					new AccelerateDecelerateInterpolator());
																	return true;
																case MotionEvent.ACTION_UP:
																case MotionEvent.ACTION_CANCEL:
																	v.animate()
																			.scaleY(1.0f)
																			.setDuration(200)
																			.setInterpolator(
																					new AccelerateDecelerateInterpolator());
																	v.clearAnimation();
																	menuButtonHandler(v.getId());
																	break;
																}
															}
															return false;
														}
													};
}
