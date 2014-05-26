package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginController
{
	private Activity		theActivity		= null;
	private RelativeLayout	loginMainLayout	= null;
	private Handler			notifyHandler	= null;

	public class LoginData
	{
		public String	mstrName		= null;
		public String	mstrPassword	= null;
	}

	public LoginController(Activity activity)
	{
		super();
		theActivity = activity;
		loginMainLayout = (RelativeLayout) activity.findViewById(R.id.login_main_layout);
		loginMainLayout.setOnTouchListener(mainLayoutTouch);
		loginHandle(activity);
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
	}

	public void setNotifyHandler(Handler handler)
	{
		notifyHandler = handler;
	}

	private void loginHandle(final Activity activity)
	{
		TextView loginBtn = (TextView) loginMainLayout.findViewById(R.id.start_login);
		loginBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText editText = (EditText) loginMainLayout.findViewById(R.id.login_name);
				String strName = editText.getText().toString();
				editText = (EditText) loginMainLayout.findViewById(R.id.login_password);
				String strPassword = editText.getText().toString();

				if (null != strName && 0 < strName.length() && null != strPassword && 0 < strPassword.length())
				{
					LoginData data = new LoginData();
					data.mstrName = strName;
					data.mstrPassword = strPassword;
					EventHandler.notify(notifyHandler, EventMessage.MSG_LOGIN, 0, 0, data);
					data = null;
					//		flipperView.close();
				}

			}
		});

	}

	private OnTouchListener	mainLayoutTouch	= new OnTouchListener()
											{

												@Override
												public boolean onTouch(View v, MotionEvent event)
												{
													switch (event.getAction())
													{
													case MotionEvent.ACTION_DOWN:
														return true;
													}
													return false;
												}
											};

}
