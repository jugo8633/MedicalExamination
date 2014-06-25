package com.medici.app.model;

import com.medici.app.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class HttpClientLogin extends HttpClientHandler
{
	private final int		LOGIN_FAIL	= -1;
	private ProgressDialog	progress	= null;
	private Response		response	= null;

	public HttpClientLogin(Activity activity)
	{
		super(activity);
		response = new Response();
	}

	public void login(final String strAccount, final String strPassword, final Handler handler)
	{
		progress = ProgressDialog.show(theActivity, theActivity.getString(R.string.sign_in), null, false);
		AsyncTask<Void, Void, String> shareRegidTask = new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params)
			{
				String strUrlParam = HttpClientConfig.LOGIN_PARAM + "=" + strAccount;
				try
				{
					HttpClientLogin.this.sendPost(HttpClientConfig.SERVER_URL, strUrlParam, response);
				}
				catch (Exception e)
				{
					onResponse(-1, null);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result)
			{

			}

		};
		shareRegidTask.execute(null, null, null);
	}

	@Override
	protected boolean onResponse(int nCode, String strMessage)
	{
		if (null != progress)
		{
			progress.dismiss();
			progress = null;
		}
		if (Type.INVALID == nCode)
		{
			EventHandler.notify(selfHandler, LOGIN_FAIL, 0, 0, null);
		}
		return false;
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case LOGIN_FAIL:
											Global.showDidlog(theActivity, null, null, "Login Fail", Type.INVALID);
											break;
										}
									}
								};

}
