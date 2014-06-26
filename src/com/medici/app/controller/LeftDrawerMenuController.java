package com.medici.app.controller;

import com.jugo.smartwidget.button.ShapButton;
import com.jugo.smartwidget.button.ShapButton.OnButtonClickedListener;
import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.model.HttpClientLogin;
import com.medici.app.model.NetworkHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

public class LeftDrawerMenuController
{
	private ProgressDialog	progress			= null;
	private Activity		theActivity			= null;
	private String			mstrSignIn			= null;
	private String			mstrCreateAccount	= null;
	private ShapButton		btnSignIn			= null;
	private boolean			mbShowSignIn		= false;
	private View			vInclude			= null;
	private Handler			notifyHandler		= null;
	private HttpClientLogin	login				= null;
	private final int[]		listMenuButton		= { R.id.RelativeLayoutLeftDrawerCreateAccount,
			R.id.RelativeLayoutLeftDrawerLogin, R.id.RelativeLayoutLeftDrawerSignInWithGoogle,
			R.id.RelativeLayoutLeftDrawerSignInWithFacebook, R.id.RelativeLayoutLeftDrawerExamination,
			R.id.RelativeLayoutLeftDrawerSingleRun };

	public LeftDrawerMenuController(Activity activity, final Handler handler)
	{
		super();
		theActivity = activity;
		notifyHandler = handler;
		initView(activity);
		login = new HttpClientLogin(theActivity);
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
		vInclude = activity.findViewById(R.id.subSignIn);
		btnSignIn = (ShapButton) vInclude.findViewById(R.id.shapButtonLogin);
		mstrSignIn = activity.getString(R.string.sign_in);
		mstrCreateAccount = activity.getString(R.string.create_account);
		btnSignIn.setOnButtonClickedListener(new OnButtonClickedListener()
		{
			@Override
			public void OnButtonClicked(boolean bSelected)
			{
				if (bSelected)
				{
					if (btnSignIn.getTag().equals("SIGN"))
					{
						startSignIn();
					}
					if (btnSignIn.getTag().equals("ACCOUNT"))
					{
						startCreateAccount();
					}
					showSignInLayout(false);
				}
			}
		});
	}

	private void startSignIn()
	{
		login.login("test@gmail.com", "sssss", null);
	}

	private void startCreateAccount()
	{
		progress = ProgressDialog.show(theActivity, theActivity.getString(R.string.create_account), null, false);

		new Thread()
		{
			public void run()
			{
				try
				{
					sleep(3000);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					progress.dismiss();
				}
			}
		}.start();
	}

	private void menuButtonHandler(int nResId)
	{
		/**
		 * we check network first.
		 */
		if (R.id.RelativeLayoutLeftDrawerCreateAccount == nResId || R.id.RelativeLayoutLeftDrawerLogin == nResId)
		{
			if (!NetworkHandler.isMobileNetworkAvailable(theActivity))
			{
				NetworkHandler.showConnectionNADialog(theActivity);
				return;
			}
		}

		switch (nResId)
		{
		case R.id.RelativeLayoutLeftDrawerCreateAccount:
			mbShowSignIn = mbShowSignIn ? false : true;
			btnSignIn.setText(mstrCreateAccount);
			btnSignIn.setTag("ACCOUNT");
			showSignInLayout(mbShowSignIn);
			break;
		case R.id.RelativeLayoutLeftDrawerLogin:
			mbShowSignIn = mbShowSignIn ? false : true;
			btnSignIn.setText(mstrSignIn);
			btnSignIn.setTag("SIGN");
			showSignInLayout(mbShowSignIn);
			break;
		case R.id.RelativeLayoutLeftDrawerPersonInfo:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_PERSON_INFO, 0, 0, null);
			break;
		case R.id.RelativeLayoutLeftDrawerExamination:
			EventHandler.notify(notifyHandler, EventMessage.MSG_SHOW_EXAMINATION, 0, 0, null);
			break;
		case R.id.RelativeLayoutLeftDrawerSingleRun:
			showSingleRunInfo();
			break;
		}
	}

	private void showSingleRunInfo()
	{
		Global.showDidlog(theActivity, notifyHandler, null, theActivity.getString(R.string.single_run_message),
				EventMessage.MSG_SINGLE_RUN_INFO);
	}

	private void showSignInLayout(boolean bShow)
	{
		mbShowSignIn = bShow;
		if (bShow)
		{
			vInclude.setVisibility(View.VISIBLE);
		}
		else
		{
			vInclude.setVisibility(View.GONE);
			btnSignIn.setClick(false);
			Global.hideIME(theActivity);
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
