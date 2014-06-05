package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class LoginController
{

	public class LoginData
	{
		public String	mstrName		= null;
		public String	mstrPassword	= null;
	}

	public LoginController(Activity activity, Handler handler)
	{
		super();
		initView(activity, handler);

	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
	}

	public void init()
	{

	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.login_main_layout);
		loginHandle(parent, handler);
	}

	private void loginHandle(final ViewGroup parent, final Handler handler)
	{
		TextView loginBtn = (TextView) parent.findViewById(R.id.start_login);
		loginBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				EditText editText = (EditText) parent.findViewById(R.id.login_name);
				String strName = editText.getText().toString();
				editText = (EditText) parent.findViewById(R.id.login_password);
				String strPassword = editText.getText().toString();

				if (null != strName && 0 < strName.length() && null != strPassword && 0 < strPassword.length())
				{
					LoginData data = new LoginData();
					data.mstrName = strName;
					data.mstrPassword = strPassword;
					EventHandler.notify(handler, EventMessage.MSG_LOGIN, 0, 0, data);
					data = null;
				}

			}
		});

	}
}
